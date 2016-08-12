import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	
	@Before
    public void setup() {
        Fixtures.deleteDatabase();
    }
	
	@Test
	public void createAndRetrieveClient() {
	    // Create a new user and save it
	    new Client("bob@orreco.com", "secret", "Bob").save();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    Client coach = Client.find("byEmail", "bob@orreco.com").first();
	    
	    // Test 
	    assertNotNull(coach);
	    assertEquals("Bob", coach.fullname);
	}
	
	
	
	@Test
	public void tryConnectAsClient() {
	    // Create a new user and save it
	    new Client("bob@orreco.com", "secret", "Bob").save();
	    
	    // Test 
	    assertNotNull(Client.connect("bob@orreco.com", "secret"));
	    assertNull(Client.connect("bob@orreco.com", "badpassword"));
	    assertNull(Client.connect("tom@orreco.com", "secret"));
	}
	
	
	
	
	@Test
	public void createPlayer() {
	    // Create a new client and save it
	    Client bob = new Client("bob@orreco.com", "secret", "Bob").save();
	    
	    // Create a new player
	    new Player("Alice", 1, null, bob).save();
	    
	    // Test that the player has been created
	    assertEquals(1, Player.count());
	    
	    // Retrieve all players created by Bob
	    List<Player> bobsPlayers = Player.find("byCoach", bob).fetch();
	    
	    // Tests
	    assertEquals(1, bobsPlayers.size());
	    Player firstPlayer = bobsPlayers.get(0);
	    assertNotNull(firstPlayer);
	    assertEquals(bob, firstPlayer.coach);
	    assertEquals("Alice", firstPlayer.playername);
	    assertEquals(new Integer(1), firstPlayer.playernumber);
	    assertNotNull(firstPlayer.dateadded);
	}
	
	
	
	
	@Test
	public void playerGPSData() {
	    // Create a new client and save it
		Client bob = new Client("bob@orreco.com", "secret", "Bob").save();
	 
	    // Create a new player
	    Player player = new Player("Alice", 1, null, bob).save();
	 
	    // create player gps data
	    new GPSData(player, 10, new Date(), 100).save();
	    new GPSData(player, 10, new Date(), 200).save();
	 
	    // Retrieve all gps data
	    List<GPSData> bobPlayerGPS = GPSData.find("byPlayer", player).fetch();
	 
	    // Tests
	    assertEquals(2, bobPlayerGPS.size());
	 
	    GPSData firstData = bobPlayerGPS.get(0);
	    assertNotNull(firstData);
	    assertEquals(10, firstData.playerIdentityNo);
	    assertEquals(100, firstData.tT_Time);
	    assertNotNull(firstData.dataDate);
	 
	    GPSData secondDataPoint = bobPlayerGPS.get(1);
	    assertNotNull(secondDataPoint);
	    assertEquals(10, secondDataPoint.playerIdentityNo);
	    assertEquals(200, secondDataPoint.tT_Time);
	    assertNotNull(secondDataPoint.dataDate);
	}
	
	
	
	
	@Test
	public void useTheGPSDataRelation() {
	    // Create a new client and save
	    Client bob = new Client("bob@orreco.com", "secret", "Bob").save();
	 
	    // Create a new player
	    Player player = new Player("Alice", 10, null, bob).save();
	 
	    // add some gps data
	    player.addGPSData(new Date(), 101);
	    player.addGPSData(new Date(10), 201);
	 
	    // Count things
	    assertEquals(1, Client.count());
	    assertEquals(1, Player.count());
	    assertEquals(2, GPSData.count());
	 
	    // Retrieve Bob's players
	    player = Player.find("byCoach", bob).first();
	    assertNotNull(player);
	 
	    // Navigate to gpsdata
	    assertEquals(2, player.gpsdata.size());
	    assertEquals(10, player.gpsdata.get(0).playerIdentityNo);
	    
	    // Delete the player
	    player.delete();
	    
	    // Check that all gpsData has been deleted
	    assertEquals(1, Client.count());
	    assertEquals(0, Player.count());
	    assertEquals(0, GPSData.count());
	}
	
	
	
	
	
	@Test
	public void fullTest() {
	    Fixtures.loadModels("data.yml");
	 
	    // Count things
	    assertEquals(3, Client.count());
	    assertEquals(4, Player.count());
	    assertEquals(3, GPSData.count());
	 
	    // Try to connect as clients
	    assertNotNull(Client.connect("bob@orreco.com", "fred"));
	    assertNotNull(Client.connect("alice@orreco.com", "secret"));
	    assertNull(Client.connect("jeff@gmail.com", "badpassword"));
	    assertNull(Client.connect("tom@gmail.com", "secret"));
	 
	    // Find all of Bob's players
	    List<Player> bobPlayers = Player.find("coach.email", "bob@orreco.com").fetch();
	    assertEquals(3, bobPlayers.size());
	 
	    // Find all gpsdata related to Bob's players
	    List<GPSData> bobsPlayersGpsdata = GPSData.find("player.coach.email", "bob@orreco.com").fetch();
	    assertEquals(3, bobsPlayersGpsdata.size());
	 
	    // Find the most recent player
	    Player frontPlayer = Player.find("order by dateadded desc").first();
	    assertNotNull(frontPlayer);
	    assertEquals("Jed Klumpp", frontPlayer.playername);
	 
	    // Check that this player has two gpsdata records
	    assertEquals(2, frontPlayer.gpsdata.size());
	 
	    // add new gpsdata for a player
	    frontPlayer.addGPSData(new Date(), 300);
	    assertEquals(3, frontPlayer.gpsdata.size());
	    assertEquals(4, GPSData.count());
	}
	
	
	
	
	
	
	@Test
	public void testTags() {
	    // Create a new client and save it
	    Client bob = new Client("bob@orreco.com", "fred", "Bob").save();
	 
	    // Create a new player
	    Player player1 = new Player("Alice", 10, null, bob).save();
	    Player player2 = new Player("Alice", 10, null, bob).save();
	 
	    // Well
	    assertEquals(0, Player.findCategorisededWith("Red").size());
	 
	    // Categorise it now
	    player1.categoriseItWith("Red").categoriseItWith("Blue").save();
	    player2.categoriseItWith("Red").categoriseItWith("Green").save();
	 
	    // Check
	    assertEquals(2, Player.findCategorisedWith("Red").size());
	    assertEquals(1, Player.findCategorisedWith("Blue").size());
	    assertEquals(1, Player.findCategorisedWith("Green").size());
	    
	    assertEquals(1, Player.findCategorisededWith("Red", "Blue").size());
	    assertEquals(1, Player.findCategorisededWith("Red", "Green").size());
	    assertEquals(0, Player.findCategorisededWith("Red", "Green", "Blue").size());
	    assertEquals(0, Player.findCategorisededWith("Green", "Blue").size());
	    
	    List<Map> cloud = Category.getCloud();
	    assertEquals(
	        "[{category=Blue, pound=1}, {category=Green, pound=1}, {category=Red, pound=2}]",
	        cloud.toString()
	    );
	}
	

}

