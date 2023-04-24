import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.Jsoup;

public class FlightFinder {

	public static void main(String[] args) {
		//started project 12/09/2021
		initialize();

	}

	
	
public static  JFrame initialize()
{
	
	JFrame mainFrame = new JFrame("Flight Finder");
	
	mainFrame.setSize(500, 500);
	JPanel p = new JPanel();

	p.add(new JLabel("Departures by Airport"));
	JTextField searchField3 = new JTextField(10);
	p.add(searchField3);
	
	JButton searchButton = new JButton("Search");
	
	JCheckBox checkbox1 = new JCheckBox("A320");//checkboxes to filter by certain aircraft
	JCheckBox checkbox2 = new JCheckBox("B738");
	
	
	
	JCheckBox sortbox1 = new JCheckBox("Sort By Airline");
	p.add(searchButton);
	p.add(checkbox1);
	p.add(checkbox2);
	
	
	p.add(sortbox1);
//	p.add(sortbox2);
//	p.add(sortButtonAirlines);
//	p.add(sortButtonAircraft);
	
	mainFrame.add(p);
	mainFrame.setVisible(true);
	
	
	
	searchButton.addActionListener(e -> {
		//findFlight(searchField, searchField2);
		departureFlights(searchField3,checkbox1,checkbox2, sortbox1);
	});
	
	
	
	
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	return mainFrame;
	
}
	



public static JFrame departureFlights(JTextField airportCode, JCheckBox a, JCheckBox b, JCheckBox c)
{
	String website = "https://flightaware.com/live/airport/" + airportCode.getText();  //scrap the data from this website
	//String website = "https://www.flightradar24.com/data/airports/den/departures";
	try {
		
		org.jsoup.nodes.Document doc = Jsoup.connect(website).userAgent("Mozilla/5.0").maxBodySize(10*1024*1024).timeout(600000).get();
		org.jsoup.select.Elements flights = doc.getElementsByClass("fullWidth airportBoard");//for flightaware
		org.jsoup.select.Elements title = doc.getElementsByClass("mainHeader airportTrackerTitle airportTrackerTitleText");
		
		int count =0;
		String line = "";
		String[] flightlines = null;
		
		
		for(org.jsoup.nodes.Element flight : flights)
		{
			
			
			if(flight.text().contains("Scheduled Departures"))
			{
				
								
				//System.out.println("Departures leaving " + title.text());
				//System.out.println(" Departures leaving " + airportCode.getText());
				
				
				//System.out.println(" Ident Aircraft To Depart Arrive");
			
				line = flight.text();
				line = line.replaceAll("ve ", "ve\n");
			
				
				flightlines = line.split("T ");
				
				
				
			}//end of schedule departures if
			
						
		}//end of flights for loop
		
		System.out.println("Departures leaving " + title.text());
		System.out.println();
		
		
		for(int i=0;i < flightlines.length; i++)
		{
			
				if(c.isSelected())//this is if no filter tick boxes selected
				{
					Arrays.sort(flightlines);
					System.out.println(flightlines[i]);
					count++;
				}
			
			
			if(a.isSelected() && (flightlines[i].contains("A320")||flightlines[i].contains("A20N")))
			{
				System.out.println(flightlines[i]);
				count++;
				
			}
			else if(b.isSelected() && flightlines[i].contains("B738"))
			{
				System.out.println(flightlines[i]);
				count++;
			}
			else if((a.isSelected() && (flightlines[i].contains("A320"))|| flightlines[i].contains("A20N")) && (b.isSelected() && flightlines[i].contains("B738")))
			{
				System.out.println(flightlines[i]);
				count++;
			}
			else if((a.isSelected() == false && b.isSelected()== false))//print out all flights
			{
				System.out.println(flightlines[i]);
				count++;
			}
				
				
			

				
		}
		System.out.println("There are " + count + " Flights");
		
		
		
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return null;
	
}






public static JFrame findFlight(JTextField origin, JTextField destination) //dead method not being used for anything at this moment
{
	
	String ori = origin.getText();
	String dest = destination.getText();
	
	try {
		
		String website = "https://flightaware.com/live/findflight?origin=" + ori + "&destination=" + dest; //scrap the data from this website
		
		
		org.jsoup.nodes.Document doc = Jsoup.connect(website).userAgent("Mozilla/5.0").maxBodySize(10*1024*1024).timeout(600000).get();
		org.jsoup.select.Elements flights = doc.select("table#Results");
		org.jsoup.select.Elements tbody = doc.select("tbody");
		
		
		 
		org.jsoup.select.Elements titles = doc.getElementsByClass("ffinder-results-title form_head_1");
		org.jsoup.select.Elements subtitles = doc.getElementsByClass("finder-table_head");
		org.jsoup.select.Elements aircrafts = tbody.select("tr");
	  System.out.println(titles.text()); //titles of the airports chosen
		//System.out.println(results);
		//System.out.println(aircrafts);
		
		 
		
		 
		 int count =0; 
		 for(org.jsoup.nodes.Element x : aircrafts)
		 {
			
			 String line = x.attributes().toString();
			
			 //21 spaces before flight code
			for(int i =22; i < line.length(); i++ )
			{
				//System.out.println("this is line " + line);
				
				if(line.charAt(i) == '-')
				{
					System.out.print("\n");
					count++;
					break;
					
				}
				else
				{
					System.out.print(line.charAt(i));
				
				}
				
				
			}
						 
			 
		 }
		 
		 System.out.println("There are " + count + " flights\n");
			
	}
	catch (Exception e )
  {
		
	}
	
	return null;
	
}
	
	
	
}
