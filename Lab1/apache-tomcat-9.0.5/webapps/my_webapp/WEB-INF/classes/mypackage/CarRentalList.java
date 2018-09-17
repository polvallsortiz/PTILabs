package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CarRentalList extends HttpServlet {



  int cont = 0;
  JSONArray rentalsArray;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    getJSONRentals();
    for(int i = 0; i < rentalsArray.size(); ++i) {
      JSONObject rental = (JSONObject) rentalsArray.get(i);
      String model_vehicle = (String) rental.get("model_vehicle");
      String sub_model_vehicle = (String) rental.get("sub_model_vehicle");
      String dies_lloguer = (String) rental.get("dies_lloguer");
      String num_vehicles = (String) rental.get("num_vehicles");
      String descompte = (String) rental.get("descompte");
      PrintWriter out = res.getWriter();
      out.println("<html><p>model_vehicle:" + model_vehicle + "</p><p>sub_model_vehicle:" + sub_model_vehicle + "</p><p>dies_lloguer:" + dies_lloguer + "</p><p>num_vehicles:"
              + num_vehicles + "</p><p>descompte:" +
              descompte +"</p> <p> </p> </html>");
    }
    PrintWriter out = res.getWriter();
    out.println("<html><a href=\"carrental_home.html\">Back</a></html>");
  }

  public void getJSONRentals() {
    JSONParser parser = new JSONParser();
    try {
      Object obj = parser.parse(new FileReader("/home/vallsortizpol/pti/jsons/car_rentals.json"));
      JSONObject jsonObject = (JSONObject) obj;
      rentalsArray = (JSONArray) jsonObject.get("rentals");
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }

}
