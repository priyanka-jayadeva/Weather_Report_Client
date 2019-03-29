//Done by Priyanka Bangalore Jayadeva
//Student ID: 1001512908

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

//import javax.json.JsonObject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.ProductType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.UnitType;
import gov.weather.graphical.xml.DWMLgen.schema.DWML_xsd.WeatherParametersType;
import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortTypeProxy;

//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;


public class Weather implements ActionListener {

	private static NdfdXMLPortTypeProxy proxy;
	private static WeatherParametersType wp;
	private JFrame frame;
	private JTextField longitudeTextField;
	private JTextField latitudeTextField;
	private JLabel longitudeLabel;
	private JLabel latitudeLabel;
	private  JLabel errorLabel;
	private JButton SubmitButton;
	private JButton RefreshButton;
	private JButton ResetButton;
	private JLabel regularLabel;
	private JLabel TemperatureLabel;
	private JLabel GeneralLabel;
	private JLabel TemperatureLabel1;
	private JLabel refreshLabel;
	private JButton ClearButton;
	private static JLabel DirectionLabel;
	private static JLabel WindSpeedLabel;
	private static JLabel DirectionLabel1;
	private static JLabel WindSpeedLabel1;
	private static JLabel MaxTemperatureLabel1;
	private static JLabel MinTemperatureLabel;
	private static JLabel MaxTemperatureLabel;
	private static JLabel MinTemperatureLabel1;
	
	public Weather() throws RemoteException {
		initialize();
		
	}
	
	
	private void initialize() {
		frame = new JFrame("Weather Info");
		frame.setBounds(100, 100, 750, 600);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		refreshLabel = new JLabel();																								// creating label to show Error and refresh data
		refreshLabel.setBounds(10, 10, 400, 25);														
		frame.add(refreshLabel);

		
		regularLabel = new JLabel("ENTER CO-ORDINATES");																								// creating label to show Error and refresh data
		regularLabel.setBounds(10, 35, 400, 25);														
		frame.add(regularLabel);
		
		latitudeLabel = new JLabel("Enter Latitude : ");																				// creating label to enter latitude
		latitudeLabel.setBounds(10, 70, 100, 30);														
		frame.add(latitudeLabel);

		latitudeTextField = new JTextField();																						// creating text field to show client response on server
		latitudeTextField.setBounds(150, 70, 250, 30);
		frame.add(latitudeTextField);

		longitudeLabel = new JLabel("Enter Longitude : ");																// creating label for user to insert alternative word for given word on server
		longitudeLabel.setBounds(10, 120, 200, 30);
		frame.add(longitudeLabel);
		
		longitudeTextField = new JTextField();																						// creating text field to show client response on server
		longitudeTextField.setBounds(150, 120, 250, 30);
		frame.add(longitudeTextField);
		
		
		GeneralLabel = new JLabel("WEATHER DETAILS");																// creating label for user to insert alternative word for given word on server
		GeneralLabel.setBounds(10, 210, 200, 30);
		frame.add(GeneralLabel);
		
		MinTemperatureLabel = new JLabel("Temperature Dew Point : ");																// creating label for user to insert alternative word for given word on server
		MinTemperatureLabel.setBounds(10, 270, 200, 30);
		frame.add(MinTemperatureLabel);
		
		MinTemperatureLabel1 = new JLabel();																// creating label for user to insert alternative word for given word on server
		MinTemperatureLabel1.setBounds(220, 270, 30, 30);
		frame.add(MinTemperatureLabel1);
		
		MaxTemperatureLabel = new JLabel("12 Hour Probability of Precipitation : ");																// creating label for user to insert alternative word for given word on server
		MaxTemperatureLabel.setBounds(10, 320, 290, 30);
		frame.add(MaxTemperatureLabel);
		
		MaxTemperatureLabel1 = new JLabel();																// creating label for user to insert alternative word for given word on server
		MaxTemperatureLabel1.setBounds(220, 320, 30, 30);
		frame.add(MaxTemperatureLabel1);
		
		DirectionLabel = new JLabel("Wind Direction : ");																// creating label for user to insert alternative word for given word on server
		DirectionLabel.setBounds(10, 370, 200, 30);
		frame.add(DirectionLabel);
		
		DirectionLabel1 = new JLabel();																// creating label for user to insert alternative word for given word on server
		DirectionLabel1.setBounds(220, 370, 30, 30);
		frame.add(DirectionLabel1);
		
		WindSpeedLabel = new JLabel("Wind Speed : ");																// creating label for user to insert alternative word for given word on server
		WindSpeedLabel.setBounds(10, 420, 200, 30);
		frame.add(WindSpeedLabel);
		
		WindSpeedLabel1 = new JLabel();																// creating label for user to insert alternative word for given word on server
		WindSpeedLabel1.setBounds(220, 420, 30, 30);
		frame.add(WindSpeedLabel1);
		
		SubmitButton = new JButton("Submit");																						// creating button for user to add alternative word for given word on server
		SubmitButton.setBounds(10, 500, 100, 30);
		SubmitButton.addActionListener(this);
		frame.add(SubmitButton);

		RefreshButton = new JButton("Refresh");																							// creating exit button for server 
		RefreshButton.setBounds(150, 500, 100, 30);
		RefreshButton.addActionListener(this);
		frame.add(RefreshButton);

		ClearButton = new JButton("Clear");																							// creating exit button for server 
		ClearButton.setBounds(290, 500, 100, 30);
		ClearButton.addActionListener(this);
		frame.add(ClearButton);

	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(SubmitButton)) {
			
				try {
					refreshLabel.setText("");
					getWeatherDetails(latitudeTextField.getText().trim(),longitudeTextField.getText().trim());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
		else if (e.getSource().equals(RefreshButton)) {
			try {
				refreshLabel.setText("");
				getWeatherDetails(latitudeTextField.getText().trim(),longitudeTextField.getText().trim());
				refreshLabel.setText("The Temperature Values are refreshed");
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (e.getSource().equals(ClearButton)) {
			refreshLabel.setText("");
			clearFields();
		}
	}
	
	private void clearFields() {
		// TODO Auto-generated method stub
		longitudeTextField.setText("");
		latitudeTextField.setText("");
		MinTemperatureLabel1.setText("");
		MaxTemperatureLabel1.setText("");
		DirectionLabel1.setText("");
		WindSpeedLabel1.setText("");
	}


	public void getWeatherDetails(String latS,String lonS) throws RemoteException
	{
		
		/*try {
			
			String lat = latitudeTextField.getText().trim();	
			String lon = longitudeTextField.getText().trim();	
			JSONObject json = readJsonFromUrl("https://api.weather.gov/points/"+lon+","+lat+"/forecast");
			JSONArray p = json.getJSONObject("properties").getJSONArray("periods");
			JSONObject array = (JSONObject) p.get(0);
			String temperature = array.getString("Min Temperature");
			String temperatureTrend = array.getString("temperatureTrend");
			String windDirection = array.getString("windDirection");
			String windSpeed = array.getString("windSpeed");
			
			
			MinTemperatureLabel1.setText(temperature+" "+" F");
			MaxTemperatureLabel1.setText(temperatureTrend); 
			DirectionLabel1.setText(windDirection);
			WindSpeedLabel1.setText(windSpeed); 

		} catch (Exception e) {
			//refreshLabel.setText("Lat/Lon values wrongly entered or empty !!!")
			refreshLabel.setText("");
		}*/
		
		
		proxy = new NdfdXMLPortTypeProxy();
		wp = new WeatherParametersType();
		wp.setMint(true); // set setMint to True
		wp.setMaxt(true); // set setMaxt to True
		wp.setTemp(true); // set setTmp to True
		wp.setDew(true); // set setDew to True
		wp.setWspd(true); // set setWspd to True
		wp.setWdir(true); // set setWdir to True
		wp.setPop12(true); // set setPop12 to True

		// taking the input(latitude)from the user
		System.out.println("Please Enter latitude:");
		
		//TODO
		//BigDecimal lat = new BigDecimal(Double.parseDouble("32.7357"), MathContext.DECIMAL64);
		
		
		try{
			
			BigDecimal lat = new BigDecimal(Double.parseDouble(latS), MathContext.DECIMAL64);
			BigDecimal lon = new BigDecimal(Double.parseDouble(lonS),MathContext.DECIMAL64);
			getWeatherDataForInputValues(lat, lon);
		}
		catch(Exception e){
			e.printStackTrace();
			refreshLabel.setText("Lat/Lon values incorrectly entered!");
		}
		// taking the input(longitude)from the user
		//TODO
		//System.out.println("Please Enter longitude(should be negative):");
		/*BigDecimal lon = new BigDecimal(Double.parseDouble("-97.1081"),MathContext.DECIMAL64);*/
		
		// lon = checkInput(lon);
		// System.out.println(lon);
		
		
	}
//https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	/*public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//			/String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}catch(Exception e){
			return null;
		}
		finally {
			is.close();
		}
	}*/
	public static void getWeatherDataForInputValues(BigDecimal lat, BigDecimal lon) throws RemoteException {

		Calendar time = new GregorianCalendar(2017, 11, 25); // Pass this as a GregorianCalendar for the Calendar to
																// understand
		time.setTime(new Date());
		System.out.println("Fetching data from SOAP Web Service... Please wait");
		ProductType productType = ProductType.fromString("time-series");
		String result = proxy.NDFDgen(lat, lon, productType, time, time,UnitType.e, wp);
		System.out.println("response "+result);
		
		Document dom = convertStringToDocument(result);
		try {
			// Displaying the result on the output screen
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			//System.out.println("Minimum Temperature: " + getValuesFromDom(dom, xpath, "temperature[@type='maximum']")); 
			// print
			MinTemperatureLabel1.setText(getValuesFromDom(dom, xpath, "temperature[@type='dew point']"));
			// the
			// minimum
																														// temp
			System.out.println("Maximum Temperature: " + getValuesFromDom(dom, xpath, "temperature[@type='maximum']")); // print
																														// the
			MaxTemperatureLabel1.setText(getValuesFromDom(dom, xpath, "temperature[@type='maximum']"));																					// maximum
																														// temp
			System.out.println("Wind Direction: " + getValuesFromDom(dom, xpath, "direction")); // print the wind
			DirectionLabel1.setText(getValuesFromDom(dom, xpath, "direction"));
			// direction
			System.out.println("Wind Speed: " + getValuesFromDom(dom, xpath, "wind-speed")); // print the wind speed
			WindSpeedLabel1.setText(getValuesFromDom(dom, xpath, "wind-speed"));
			System.out.println("Temperature Dew point: " + getValuesFromDom(dom, xpath, "temperature[@type='dew point']")); // print
																													// the
																													// dew
																													// point
																													// temperature
			System.out.println("12 Hour Probability of Precipitation:"
					+ getValuesFromDom(dom, xpath, "probability-of-precipitation"));
			MaxTemperatureLabel1.setText(getValuesFromDom(dom, xpath, "probability-of-precipitation"));
			String command = isRefreshed();
			/*if (command.trim().toLowerCase().equals("yes")) {

				getWeatherDataForInputValues(lat, lon);
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String arg[]) {
		
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Weather window = new Weather();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		
	}
	
	
	//converts the received data into to xml document
		
	private static Document convertStringToDocument(String xmlStr) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder;  
	        try  
	        {  
	            builder = factory.newDocumentBuilder();  
	            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
	            return doc;
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return null;
	    }
		
		//extracts the data from the xml
		private static String getValuesFromDom(Document doc, XPath xpath, String typeAttr) {				// Function definition to get the value of POP12
	       
			String attrValue = null;
	        try {
	            XPathExpression xpathExpression = xpath.compile("/dwml/data/parameters/"+typeAttr+"/value/text()");
	            attrValue = (String) xpathExpression.evaluate(doc, XPathConstants.STRING);
	            
	        } catch (XPathExpressionException e) {
	            e.printStackTrace();
	        }
	        if(attrValue.equals(null)||attrValue.equals("")){
	        	return "NA";
	        }
	        return attrValue;
	    }
		
		//asks the user, if they want to refresh the request
		private static String isRefreshed(){
			return null;
			/*Scanner scanInput = new Scanner(System.in);
			System.out.println("Do you want to Refresh");
			System.out.println("Please Enter yes if you want to refresh else enter No");
			String command = scanInput.next();
			return command;*/
			
		}

}

//Citations
// https://www.youtube.com/watch?v=11iGyrvBhzc
/*https://stackoverflow.com/questions/12395281/convert-double-to-bigdecimal-and-set-bigdecimal-precision
https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
http://java2a.blogspot.com/2015/03/consuming-global-weather-web-service.html
http://www.texantek.com/forums/java/6147-creating-a-weather-webservice-client
https://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php
https://graphical.weather.gov/xml/
https://www.tutorialspoint.com/java_xml/java_xpath_parse_document.htm
http://www.journaldev.com/1237/java-convert-string-to-xml-document-and-xml-document-to-string
*/
