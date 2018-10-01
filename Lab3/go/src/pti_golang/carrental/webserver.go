package main

import (
    "fmt"
    "log"
    "net/http"
    "github.com/gorilla/mux"
    "encoding/json"
    "io"
    "io/ioutil"
    "encoding/csv"
    "os"
    "bufio"
)

type Rental struct {
	CarMaker string
	CarModel string
	NumberOfDays string
	NumberOfUnits string
}



func main() {

	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/", Home)
	router.HandleFunc("/new_rental",NewRental)
	router.HandleFunc("/list_rentals",readToFile)

	log.Fatal(http.ListenAndServe(":8080", router))
}


func Home(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Usage")
}

func NewRental(w http.ResponseWriter, r *http.Request) {
	ReadFromRequest(w,r)
}

func ReadFromRequest(w http.ResponseWriter, r *http.Request) {
	var rental Rental
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &rental); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
       writeToFile(w,rental)
    }
}



func writeToFile(w http.ResponseWriter, rental Rental) {
    file, err := os.OpenFile("rentals.csv", os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    if err!=nil {
        json.NewEncoder(w).Encode(err)
        return
    }
    writer := csv.NewWriter(file)
    var data1 = []string{rental.CarMaker,rental.CarModel,rental.NumberOfUnits,rental.NumberOfDays}
    writer.Write(data1)
    writer.Flush()
    file.Close()
}

func readToFile(w http.ResponseWriter, r *http.Request) {
	file, err := os.Open("rentals.csv")
	var lines []Rental
    if err!=nil {
    json.NewEncoder(w).Encode(err)
    return
    }
    reader := csv.NewReader(bufio.NewReader(file))
    for {
        record, err := reader.Read()
        if err == io.EOF {
                break
            }
            lines = append(lines, Rental{CarMaker: record[0], CarModel: record[1], NumberOfUnits: record[2], NumberOfDays: record[3]})
    }
    json.NewEncoder(w).Encode(lines)
}
