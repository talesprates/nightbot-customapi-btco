package btco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

class Bot extends TimerTask {
	private JSONObject jsonObject;
	private ArrayList<String> players = new ArrayList<String>();

	public Bot(String url) {
		this.jsonObject = getJSON(url);
	}

	public String getMap() {
		JSONObject message, serverinfo;

		message = (JSONObject) this.jsonObject.get("message");
		serverinfo = (JSONObject) message.get("SERVER_INFO");
		String mapcode = (String) serverinfo.get("map");

		String mapname = new String();
		switch (mapcode) {
		// Mapas Vanilla

		// Zavod
		case "MP_Abandoned":
			mapname = "Zavodod 311";
			break;
		// Lancang Dam
		case "MP_Damage":
			mapname = "Lancang Dam";
			break;
		// Flood Zone
		case "MP_Flooded":
			mapname = "Flood Zone";
			break;
		// Golmud Railway
		case "MP_Journey":
			mapname = "Golmud Railway";
			break;
		// Paracel Storm
		case "MP_Naval":
			mapname = "Paracel Storm";
			break;
		// Operation Locker
		case "MP_Prison":
			mapname = "Operation Locker";
			break;
		// Hainan Resort
		case "MP_Resort":
			mapname = "Hainan Resort";
			break;
		// Siege of Shanghai
		case "MP_Siege":
			mapname = "Siege of Shanghai";
			break;
		// Rogue Transmission
		case "MP_TheDish":
			mapname = "Rogue Transmission";
			break;
		// Dawnbreaker
		case "MP_Tremors":
			mapname = "Dawnbreaker";
			break;

		// China Rising

		// Silk Road
		case "XP1_001":
			mapname = "Silk Road";
			break;
		// Altai Range
		case "XP1_002":
			mapname = "Altai Range";
			break;
		// Guilin Peak
		case "XP1_003":
			mapname = "Guilin Peak";
			break;
		// Dragon Pass
		case "XP1_004":
			mapname = " Dragon Pass";
			break;

		// Second Assault

		// Caspian Border
		case "XP0_Caspian":
			mapname = "Caspian Border";
			break;
		// Operation Metro
		case "XP0_Metro":
			mapname = "Operation Metro";
			break;
		// Operation Firestorm
		case "XP0_Firestorm":
			mapname = "Operation Firestorm";
			break;
		// Gulf of Oman
		case "XP0_Oman":
			mapname = "Gulf of Oman";
			break;

		// Naval Strike

		// Lost Islands
		case "XP2_001":
			mapname = "Lost Islands ";
			break;
		// Nansha Strike
		case "XP2_002":
			mapname = "Nansha Strike";
			break;
		// Wave Breaker
		case "XP2_003":
			mapname = "Wave Breaker";
			break;
		// Operation Mortar
		case "XP2_004":
			mapname = "Operation Mortar";
			break;

		// Dragon's Teeth

		// Lumphini Garden
		case "XP3_UrbanGdn":
			mapname = "Lumphini Garden";
			break;
		// Pearl Market
		case "XP3_MarketPl":
			mapname = "Pearl Market";
			break;
		// Propaganda
		case "XP3_Prpganda":
			mapname = "Propaganda";
			break;
		// Sunken Dragon
		case "XP3_WtrFront":
			mapname = "Sunken Dragon";
			break;

		// Last Stand

		// Giants of Karelia
		case "XP4_WlkrFtry":
			mapname = "Giants of Karelia";
			break;
		// Hangar 21
		case "XP4_Titan":
			mapname = "Hangar 21";
			break;
		// Opeartion Whiteout
		case "XP4_Arctic":
			mapname = "Opeartion Whiteout";
			break;
		// Hammerhead
		case "XP4_SubBase":
			mapname = "Hammerhead";
			break;

		// Zavod: Graveyard Shift
		case "XP5_Night_01":
			mapname = "Zavod: Graveyard Shift";
			break;

		// Opearation Outbreak
		case "XP6_CMP":
			mapname = "Opearation Outbreak";
			break;
		// Dragon Valley
		case "XP7_Valley":
			mapname = "Dragon Valley";
			break;

		default:
			mapname = "Avise o patodomau e reporte o mapa";
			break;
		}

		try {
			System.out.println(mapname);
			PrintWriter out = new PrintWriter("bot/asqd/map.html");
			out.println(mapname);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapname;
	}

	public String getTrackedMessage() {
		Integer trackCounter = new Integer(0);
		this.players = new ArrayList<String>();
		try {
			BufferedReader trackReader = new BufferedReader(new FileReader("Tracklist.txt"));
			String line;

			while ((line = trackReader.readLine()) != null) {
				String words[] = line.split(" ");

				if (getPlayerByPersonaID(Integer.parseInt(words[1]))) {
					this.players.add(words[0]);
					trackCounter++;
				}

			}
			trackReader.close();
		} catch (Exception e) {
			System.out.println("Arquivo 'Tracklist.txt' não encontrado.");
		}

		String message = new String();

		switch (trackCounter) {
		case 0:
			message = "Hoje os cara tão fraco. Ninguém online.";
			break;
		case 1:
			message = "Só clica panelando. Só um online.";
			break;
		case 2:
			message = "Deve ser o FIREHAWK e o HOLANDES. Dois online";
			break;
		case 3:
			message = "Se organiza da pra ganhar tranquilo.";
			break;
		case 4:
			message = "Panela online: Quatro tankeiros online.";
			break;
		default:
			message = "Chama a gangue porque lá panelaram de vez. Mais de cinco online.";
		}

		try {
			System.out.println(message);
			PrintWriter out = new PrintWriter("bot/asqd/tracklist.html");
			out.println(message);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return message;
	}

	public String[] getTrackedPlayers() {
		try {
			String[] playerList = null;
			PrintWriter playerHTML = new PrintWriter("bot/asqd/online.html");
			Iterator<String> playerIterator = this.players.iterator();
			while (playerIterator.hasNext()) {
				String playerName = playerIterator.next();
				playerHTML.print(playerName + " ");
				System.out.print(playerName + " ");
			}
			playerHTML.close();
			return playerList;
		} catch (Exception e) {
			System.out.println("Problemas ao criar o arquivo 'online.html'");
			return null;
		}
	}

	private boolean getPlayerByPersonaID(Integer personaID) {
		JSONObject message;
		JSONArray serverinfo;

		message = (JSONObject) this.jsonObject.get("message");
		serverinfo = (JSONArray) message.get("SERVER_PLAYERS");

		Iterator<JSONObject> playerIterator = serverinfo.iterator();

		while (playerIterator.hasNext()) {
			JSONObject player = playerIterator.next();
			if (Integer.parseInt(player.get("personaId").toString()) == personaID)
				return true;
		}
		return false;
	}

	private JSONObject getJSON(String serverJson) {
		URL url;

		try {
			StringBuilder sb = new StringBuilder();

			url = new URL(serverJson);
			URLConnection conn = url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			br.close();

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(sb.toString());
			return jsonObject;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void run() {
		String map = this.getMap();
		String panela = this.getTrackedMessage();
		String online[] = this.getTrackedPlayers();
	}

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(
				new Bot("http://battlelog.battlefield.com/bf4/servers/show/pc/3b513dc3-fe79-4c06-8696-4719c4e8a860/?json=1"),
				0, 62000);
	}

}
