package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CarRentalNew extends HttpServlet {

  String model_vehicle;
  String sub_model_vehicle;
  String dies_lloguer;
  String num_vehicles;
  String descompte;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    getDades(req);
    PrintWriter out = res.getWriter();
    out.println("<html><p>model_vehicle:" + model_vehicle + "</p><p>sub_model_vehicle:" + sub_model_vehicle + "</p><p>dies_lloguer:" + dies_lloguer + "</p><p>num_vehicles:"
            + num_vehicles + "</p><p>descompte:" +
           descompte +"</p> <a href=\"carrental_form_new.html\">Back</a> </html>");
  }

  public void getDades(HttpServletRequest req) {
    model_vehicle = req.getParameter("model_vehicle");
    sub_model_vehicle = req.getParameter("sub_model_vehicle");
    dies_lloguer = req.getParameter("dies_lloguer");
    num_vehicles = req.getParameter("num_vehicles");
    descompte = req.getParameter("descompte");
    saveJSON();
  }

  public void saveJSON() {
    JSONObject object = new JSONObject();
    object.put("model_vehicle",model_vehicle);
    object.put("sub_model_vehicle",sub_model_vehicle);
    object.put("dies_lloguer",dies_lloguer);
    object.put("num_vehicles",num_vehicles);
    object.put("descompte",descompte);
    JSONParser parser = new JSONParser();
    try {
      Object obj = parser.parse(new FileReader("/home/vallsortizpol/pti/jsons/car_rentals.json"));
      JSONObject jsonObject = (JSONObject) obj;
      JSONArray rentals = (JSONArray) jsonObject.get("rentals");
      JSONArray newRentals = new JSONArray();
      JSONObject tempJson;
      for (int i = 0; i < rentals.size(); ++i) {
        tempJson = (JSONObject) rentals.get(i);
        newRentals.add(tempJson);
      }
      newRentals.add(object);
      JSONObject fin = new JSONObject();
      fin.put("rentals",newRentals);
      try (FileWriter file = new FileWriter("/home/vallsortizpol/pti/jsons/car_rentals.json")) {
        fin.writeJSONString(fin,file);
        file.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
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

    System.out.print(object);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
