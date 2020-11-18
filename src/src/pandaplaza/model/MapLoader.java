package pandaplaza.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import pandaplaza.controller.GameController;
import pandaplaza.controller.MainMenuController;
import pandaplaza.view.DrawableArmchair;
import pandaplaza.view.DrawableChocolateMachine;
import pandaplaza.view.DrawableChocolatePanda;
import pandaplaza.view.DrawableEntrance;
import pandaplaza.view.DrawableExit;
import pandaplaza.view.DrawableField;
import pandaplaza.view.DrawableGamblerPanda;
import pandaplaza.view.DrawableGameMachine;
import pandaplaza.view.DrawableLazyPanda;
import pandaplaza.view.DrawableOrangutan;
import pandaplaza.view.DrawablePanda;
import pandaplaza.view.DrawableWardrobe;
import pandaplaza.view.DrawableWeaktile;

public class MapLoader {
	/**
	 *  We use LinkedHashMap instead of HashMap to access the elements in the order of insertion.
	 *  Because the order is important when we want to compare the outputs.
	 */
	private LinkedHashMap<String, Object> objects = new LinkedHashMap<>();
	
	/**
	 * Store controllers so we can give data to them.
	 */
	private GameController gameController;
	private MainMenuController mainMenuController;
	
	/**
	 * These values are specified in the map file, and used to transfrom the coordinates.
	 */
	private double scaleCoords, TranslateX, TranslateY, width, height;
	
	public MapLoader(GameController gameController, MainMenuController mc) {
		scaleCoords = TranslateX = TranslateY = 1;
		width = 1920;
		height = 1080;
		this.gameController = gameController;
		mainMenuController = mc;
	}
	public void ReadMap(String path) throws IOException {
		objects.clear();
		File file = new File(path);
		//System.out.println(file.getCanonicalPath());
		BufferedReader br = new BufferedReader(new FileReader(file));
		String currentLine = null;
		while ( (currentLine = br.readLine()) != null) {
			//System.out.println(currentLine);
			if (currentLine.startsWith("#") || currentLine.isEmpty()) {continue;}
			else {
				//System.out.println(currentLine);
				ParseLine(currentLine);
			}
		}
		br.close();
	}
	private void ParseLine(String currentLine) throws IOException {
		//-----------------------------------------------------------------------
		// ----------------------------General commands--------------------------
		/*
		 *  TODO: random -> need modifications in other classes too
		 *  	  load   -> we don't use it now
		 *  	  loadmap  -> we don't use it now
		 */
		
		// random
		if (currentLine.startsWith("random ")) {
			String param = currentLine.split(" ")[1];
			/*if (param.equals("on")) { Main.randomState = true; }
			else if (param.equals("off")) { Main.randomState = false; }*/
		}
		
		// load
		else if (currentLine.startsWith("load ")) {
			String inFile = currentLine.split(" ")[1];
			throw new UnsupportedOperationException("The command: load is not implemented yet");
		}
		
		// loadmap
		else if (currentLine.startsWith("loadmap ")) {
			String inFile = currentLine.split(" ")[1];
			throw new UnsupportedOperationException("The command: loadmap is not implemented yet");
		}
		
		// getstate
		else if (currentLine.startsWith("getstate ")) {
			String objName = currentLine.split(" ")[1];
			System.out.println(objects.get(objName).toString());
		}
		
		// save
		else if (currentLine.startsWith("save ")) {
			String outfileName = currentLine.split(" ")[1];
			BufferedWriter bw = new BufferedWriter(new FileWriter("tests_out/"+outfileName));
			for (Map.Entry<String, Object> it : objects.entrySet()) {
				bw.write(it.getValue().toString());
			}
			bw.flush();
			bw.close();
			//System.out.println("File saved!");
		}
		
		// Get the coordinate transform parameters
		else if (currentLine.startsWith("scalecoords ")) {
			scaleCoords = Double.parseDouble(currentLine.split(" ")[1]); 
		}
		else if (currentLine.startsWith("translatex ")) {
			TranslateX = Double.parseDouble(currentLine.split(" ")[1]); 
		}
		else if (currentLine.startsWith("translatey ")) {
			TranslateY = Double.parseDouble(currentLine.split(" ")[1]); 
		}
		else if (currentLine.startsWith("resolution ")) {
			String[] params = currentLine.split(" ");
			width = Double.parseDouble(params[1]);
			height = Double.parseDouble(params[2]);
		}

		
		// ----------------------------END OF General commands-----------------
		
		
		// ----------------------------Panda related commands-----------------
		
		// create panda
		else if (currentLine.startsWith("create panda ")) {
			String[] params = currentLine.split(" ");
			String name = params[2];
			String gameName = params[3];
			String leaderName = params[4];
			String fieldName = params[5];
			
			Panda newPanda = new Panda();
			newPanda.setName(name);
			newPanda.setGame((Game)objects.get(gameName));
			if (leaderName.equals("null")) {newPanda.setLeader(null);}
			else { newPanda.setLeader((Orangutan)objects.get(leaderName)); }
			if (fieldName.equals("null")) {newPanda.setField(null);}
			else {
				Field field2 = (Field)objects.get(fieldName);
				newPanda.setField(field2);
				field2.setAnimal(newPanda);
			}
			objects.put(name, newPanda);
			
			// create the graphics object
			DrawablePanda dp = new DrawablePanda(newPanda);
			gameController.addDrawable(dp);
			
			
		}
		
		// create lazypanda
		else if (currentLine.startsWith("create lazypanda ")) {
			String[] params = currentLine.split(" ");
			String name = params[2];
			String gameName = params[3];
			String leaderName = params[4];
			String fieldName = params[5];
			int energy;
			try {
				energy = Integer.parseInt(params[6]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("create lazypanda energy parameter error");
			}
			boolean sitting;
			if (params[7].equals("true")) {sitting = true;}
			else if (params[7].equals("false")) {sitting = false;}
			else {throw new IllegalArgumentException("create lazypanda sitting parameter error");}
			
			LazyPanda newPanda = new LazyPanda();
			newPanda.setGame((Game)objects.get(gameName));
			newPanda.setName(name);
			if (leaderName.equals("null")) {newPanda.setLeader(null);}
			else { newPanda.setLeader((Orangutan)objects.get(leaderName)); }
			if (fieldName.equals("null")) {newPanda.setField(null);}
			else {
				Field field2 = (Field)objects.get(fieldName);
				newPanda.setField(field2);
				field2.setAnimal(newPanda);
			}
			newPanda.setEnergy(energy);
			newPanda.setSitting(sitting);
			objects.put(name, newPanda);
			
			// create the graphics object
			DrawableLazyPanda dl = new DrawableLazyPanda(newPanda);
			gameController.addDrawable(dl);
		}
		
		// set lp_energy
		else if (currentLine.startsWith("set lp_energy ")) {
			String[] params = currentLine.split(" ");
			String lpName = params[2];
			int energy;
			try {
				energy = Integer.parseInt(params[3]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("set lp_enerty energy parameter error");
			}
			((LazyPanda)objects.get(lpName)).setEnergy(energy);
		}
		
		// set lp_sitting
		else if (currentLine.startsWith("set lp_sitting ")) {
			String[] params = currentLine.split(" ");
			String lpName = params[2];
			boolean sitting;
			if (params[3].equals("true")) {sitting = true;}
			else if (params[3].equals("false")) {sitting = true;}
			else {throw new IllegalArgumentException("set lp_sitting sitting parameter error");}

			((LazyPanda)objects.get(lpName)).setSitting(sitting);
		}
		
		// create gamblerpanda
		else if (currentLine.startsWith("create gamblerpanda ")) {
			String[] params = currentLine.split(" ");
			String name = params[2];
			String gameName = params[3];
			String leaderName = params[4];
			String fieldName = params[5];
			
			GamblerPanda newPanda = new GamblerPanda();
			newPanda.setGame((Game)objects.get(gameName));
			newPanda.setName(name);
			if (leaderName.equals("null")) {newPanda.setLeader(null);}
			else { newPanda.setLeader((Orangutan)objects.get(leaderName)); }
			if (fieldName.equals("null")) {newPanda.setField(null);}
			else {
				Field field2 = (Field)objects.get(fieldName);
				newPanda.setField(field2);
				field2.setAnimal(newPanda);
			}
			objects.put(name, newPanda);
			
			// graphics
			DrawableGamblerPanda dg = new DrawableGamblerPanda(newPanda);
			gameController.addDrawable(dg);
			
		}
		
		// create chocolatePanda
		else if (currentLine.startsWith("create chocolatepanda ")) {
			String[] params = currentLine.split(" ");
			String name = params[2];
			String gameName = params[3];
			String leaderName = params[4];
			String fieldName = params[5];
			
			ChocolatePanda newPanda = new ChocolatePanda();
			newPanda.setGame((Game)objects.get(gameName));
			newPanda.setName(name);
			if (leaderName.equals("null")) {newPanda.setLeader(null);}
			else { newPanda.setLeader((Orangutan)objects.get(leaderName)); }
			if (fieldName.equals("null")) {newPanda.setField(null);}
			else {
				Field field2 = (Field)objects.get(fieldName);
				newPanda.setField(field2);
				field2.setAnimal(newPanda);
			}
			objects.put(name, newPanda);
			
			DrawableChocolatePanda dc = new DrawableChocolatePanda(newPanda);
			gameController.addDrawable(dc);
		}

		
		// ----------------------------END OF Panda related commands-----------------
		
		
		//-----------------------------------Orangutan related commands-----------------
		
		// create orangutan
		else if (currentLine.startsWith("create orangutan ")) {
			
			String[] params = currentLine.split(" ");
			String name = params[2];
			String gameName = params[3];
			String fieldName = params[4];
			
			Orangutan newOrangutan = new Orangutan();
			newOrangutan.setGame((Game)objects.get(gameName));
			newOrangutan.setName(name);
			if (fieldName.equals("null")) {newOrangutan.setField(null);}
			else {
				Field field2 = (Field)objects.get(fieldName);
				newOrangutan.setField(field2);
				field2.setAnimal(newOrangutan);
			}
			objects.put(name, newOrangutan);
			
			gameController.addDrawable(new DrawableOrangutan(newOrangutan));
		}
		else if (currentLine.startsWith("mark orangutanfield1 ")) {
			String[] params = currentLine.split(" ");
			mainMenuController.setO1Field((Field)objects.get(params[2]));
			
		}
		else if (currentLine.startsWith("mark orangutanfield2 ")) {
			String[] params = currentLine.split(" ");
			mainMenuController.setO2Field((Field)objects.get(params[2]));
			
		}
		// add panda
		else if (currentLine.startsWith("add panda ") || currentLine.startsWith("panda add ")) {
			String[] params = currentLine.split(" ");
			String orangutanName = params[2];
			String pandaName = params[3];
			((Orangutan)objects.get(orangutanName)).addPanda((Panda)objects.get(pandaName));
		}
		
		// release pandas
		else if (currentLine.startsWith("release pandas ")) {
			String[] params = currentLine.split(" ");
			String orangutanName = params[2];
			((Orangutan)objects.get(orangutanName)).releasePandas();
		}
		
		// set_cooldown           ----->  '_'char is a typo (  'set cooldown' would be the correct form),
		//                                but I don't want to modify the test cases and all the documentations.
		// 								  we accept both version
		else if (currentLine.startsWith("set_cooldown ") || currentLine.startsWith("set cooldown ")) {
			String[] params = currentLine.split(" ");
			String orangutanName = null;
			int cd = 0;
			// set_cooldown case
			if (params.length == 3) {
				orangutanName = params[1];
				try {
					cd = Integer.parseInt(params[2]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("set cooldown cd parameter error");
				}
			}
			// set cooldown case
			if (params.length == 4) {
				orangutanName = params[2];
				try {
					cd = Integer.parseInt(params[3]);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("set cooldown cd parameter error");
				}
			}
			((Orangutan)objects.get(orangutanName)).setCooldown(cd);
		}
		
		//----------------------------END OF Orangutan related commands-----------------

		
		
		//----------------------------- ALL ANIMAL COMMANDS --------------------------
		// set a_field
		else if (currentLine.startsWith("set a_field ")) {
			String[] params = currentLine.split(" ");
			String animalName = params[2];
			String fieldName = params[3];
			//System.out.println(currentLine);
			((Animal)objects.get(animalName)).setField((Field)objects.get(fieldName));
			((Field)objects.get(fieldName)).setAnimal((Animal)objects.get(animalName));
		}
		
		// step
		else if (currentLine.startsWith("step ")) {
			String[] params = currentLine.split(" ");
			String animalName = params[1];
			String fieldName = params[2];
			((Animal)objects.get(animalName)).step((Field)objects.get(fieldName));
		}
		//-----------------------------END OF ALL ANIMAL COMMANDS --------------------------
		
		
		
		//-----------------------------------FIELD RELATED COMMANDS---------------------
		
		//Field commands
		
		//create field
		else if(currentLine.startsWith("create field ")) {
			String[] params = currentLine.split(" "); //split line, first 2 pieces are not parameters
			String name = params[2]; //name of field
			String animalName = params[3]; //animal's name, which is standing on the field
			Field field = new Field(); //create new field
			
			field.setName(name); //set field's name
			//set field's animal
			if(animalName.equals("null")) {field.setAnimal(null);}
			else {field.setAnimal((Animal)objects.get(animalName));}
			
			field.center = makeCenterPointFromArrLastTwo(params);
			
			
			
			Color color = makeColorFromArr(4, params);                         
			DrawableField df = new DrawableField(color, field);
			addVerticesFromArr(7, df, params);
			gameController.addDrawable(df);
			field.setVerts(df.getVertices());
			objects.put(name, field);//put the created field in the objects hashmap
		}
		
		//set field's animal & animal's field
		else if(currentLine.startsWith("set f_animal ")) {
			String[] params = currentLine.split(" ");//split line, first 2 pieces are not parameters
			String fieldName = params[2]; //name of field
			String animalName = params[3]; //name of animal
			//set field's animal
			((Field)objects.get(fieldName)).setAnimal((Animal)objects.get(animalName));
			//set animal's field
			((Animal)objects.get(animalName)).setField((Field)objects.get(fieldName));
		}
		
		//WeakTile commands
		
		//create WeakTile
		else if(currentLine.startsWith("create weaktile ")) {
			String[] params = currentLine.split(" "); //split line, first 2 pieces are not parameters
			String name = params[2]; //name of weaktile
			String animalName = params[3]; //name of animal standing on the weaktile
			int life = -1; //life of weaktile (>= 0), if it remains -1, the input parameter was invalid
			WeakTile wt = new WeakTile(); //create new weaktile
			
			//trying to set weaktile's life
			try {
				life = Integer.parseInt(params[4]);
				wt.setLife(life);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("life parameter invalid");
			}
			wt.setName(name); //setting weaktile's name
			//set weaktile's animal
			if(animalName.equals("null")) {wt.setAnimal(null);}
			else {wt.setAnimal((Animal)objects.get(animalName));}
			wt.setLife(wt.getLife()+1); // we decreased the life by 1 above in the setAnimal method. Add that life back.
			
			wt.center = makeCenterPointFromArrLastTwo(params);
			
			
			DrawableWeaktile dw = new DrawableWeaktile(makeColorFromArr(5, params), wt);
			addVerticesFromArr(8, dw, params);
			gameController.addDrawable(dw);
			
			wt.setVerts(dw.getVertices());
			objects.put(name, wt);//put the created weaktile in the objects hashmap
		} 
		
		//set weaktile's animal & animal's field to this weaktile
		else if(currentLine.startsWith("set wt_animal ")) {
			String[] params = currentLine.split(" ");//split line, first 2 pieces are not parameters
			String weaktileName = params[2]; //name of weaktile
			String animalName = params[3]; //name of animal
			//set weaktile's animal
			((WeakTile)objects.get(weaktileName)).setAnimal((Animal)objects.get(animalName));
			//set animal's field to this weaktile
			((Animal)objects.get(animalName)).setField((WeakTile)objects.get(weaktileName));
		}
		
		//set weaktiles life
		else if(currentLine.startsWith("set life ")) {
			String[] params = currentLine.split(" ");//split line, first 2 pieces are not parameters
			String wtName = params[2]; //weaktile's name we want to modify
			int life; //new value of weaktile's life
			
			//trying to set weaktiles life
			try {
				life = Integer.parseInt(params[3]);
				((WeakTile)objects.get(wtName)).setLife(life);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("life parameter invalid, couldn't set");
			}
		}
		
		//GameMachine commands
		
		//create gamemachine
		else if(currentLine.startsWith("create gamemachine ")) {
			String[] params = currentLine.split(" "); //split line, first 2 pieces are not parameters
			String name = params[2]; // gamemachine's name
			boolean ringing;
			
			//true= gamemachine is ringing, false= gamemachine is not ringing
			if (params[3].equals("true")) {ringing = true;}
			else if (params[3].equals("false")) {ringing = false;}
			else {throw new IllegalArgumentException("create gamemachine ringing parameter error");}
			
			GameMachine gm = new GameMachine(); //create new gamemachine
			//setting gamemachines attributes
			gm.setRinging(ringing);
			gm.setName(name);
			gm.center = makeCenterPointFromArrLastTwo(params);
			
			DrawableGameMachine dgm = new DrawableGameMachine(makeColorFromArr(4, params), gm);
			addVerticesFromArr(7, dgm, params);
			gameController.addDrawable(dgm);
			
			gm.setVerts(dgm.getVertices());
			objects.put(name, gm); //put the created gamemachine in the objects hashmap
		}
		
		//set gamemachine tickcounter
		else if(currentLine.startsWith("set gm_tickcounter ")) {
			String[] params = currentLine.split(" "); //split line, first 2 pieces are not parameters
			String name = params[2]; //name of gamemachine
			int tc; //new tickcounter value
			
			//trying to set gamemachine's tickcounter
			try {
				tc = Integer.parseInt(params[3]);
				((GameMachine)objects.get(name)).setTickCounter(tc);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("tickcounter parameter invalid, couldn't set");
			}
		}
		
		//set gamemachine ringing
		else if(currentLine.startsWith("set gm_ringing ")) {
			String[] params = currentLine.split(" "); //split line, first 2 pieces are not parameters
			String name = params[2]; //name of gamemachine
			boolean ringing;
			
			//true= gamemachine is ringing, false= gamemachine is not ringing
			if (params[3].equals("true")) {ringing = true;}
			else if (params[3].equals("false")) {ringing = false;}
			else {throw new IllegalArgumentException("gamemachine (set) ringing parameter error");}
			
			((GameMachine)objects.get(name)).setRinging(ringing);
		}
		
		//ChocolateMachine commands
		
		//create chocolatemachine
		else if(currentLine.startsWith("create chocolatemachine ")) {
			String[] params  = currentLine.split(" ");
			String name = params[2];
			boolean whistling;
			if (params[3].equals("true")) {whistling = true;}
			else if (params[3].equals("false")) {whistling = false;}
			else {throw new IllegalArgumentException("create chocolatemachine whistling parameter error");}
			ChocolateMachine cm = new ChocolateMachine();
			cm.setWhistling(whistling);
			cm.setName(name);
			cm.center = makeCenterPointFromArrLastTwo(params);
			
			DrawableChocolateMachine dcm = new DrawableChocolateMachine(makeColorFromArr(4, params), cm);
			addVerticesFromArr(7, dcm, params);
			gameController.addDrawable(dcm);
			
			cm.setVerts(dcm.getVertices());
			objects.put(name, cm);
		}
		
		//set cm_tickcounter
		else if(currentLine.startsWith("set cm_tickcounter ")) {
			String[] params = currentLine.split(" ");
			String name = params[2];
			String value = params[3];
			int counter;
			try {
				counter = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("set cm_tickcounter int_value parameter error");
			}
			((ChocolateMachine)objects.get(name)).setTickCounter(counter);
		}

		//set cm_whistling
		else if(currentLine.startsWith("set cm_whistling ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			boolean whistling;
			if(params[3].equals("true")) {whistling = true;}
			else if(params[3].equals("false")) {whistling = false;}
			else {throw new IllegalArgumentException("set cm_whistling true/false parameter error");}
			((ChocolateMachine)objects.get(name)).setWhistling(whistling);
		}
		
		//Armchair commands
		
		//create armchair
		else if(currentLine.startsWith("create armchair ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			Armchair a = new Armchair();
			a.setName(name);
			a.center = makeCenterPointFromArrLastTwo(params);
			
			
			DrawableArmchair da = new DrawableArmchair(makeColorFromArr(3, params), a);
			addVerticesFromArr(6, da, params);
			gameController.addDrawable(da);
			
			a.setVerts(da.getVertices());
			objects.put(name, a);
		}
		
		//Entrance commands
		
		//create entrance
		else if(currentLine.startsWith("create entrance ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			Entrance e = new Entrance();
			e.setName(name);
			e.center = makeCenterPointFromArrLastTwo(params);
			
			DrawableEntrance de = new DrawableEntrance(makeColorFromArr(3, params), e);
			
			addVerticesFromArr(6, de, params);
			gameController.addDrawable(de);
			e.setVerts(de.getVertices());
			objects.put(name, e);
		}
		
		//Exit commands
		
		//create exit
		else if(currentLine.startsWith("create exit ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			String entrancepair = params[3];
			String game = params[4];
			Exit exit = new Exit();
			Entrance pair = (Entrance) objects.get(entrancepair);
			Game g = (Game) objects.get(game);
			exit.setEntrance(pair);
			exit.setGame(g);
			exit.setName(name);
			exit.center = makeCenterPointFromArrLastTwo(params);
			
			
			DrawableExit de = new DrawableExit(makeColorFromArr(5, params), exit);
			addVerticesFromArr(8, de, params);
			gameController.addDrawable(de);
			exit.setVerts(de.getVertices());
			objects.put(name, exit);
		}
		
		//Wardrobe commands
		
		//create wardrobe
		else if(currentLine.startsWith("create wardrobe ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			String wpair = params[3];
			String animal = params[4];
			Wardrobe wardrobe = new Wardrobe();
			Wardrobe pair = (Wardrobe) objects.get(wpair);
			wardrobe.setPair(pair);
			wardrobe.setName(name);
			if(animal.equals("null")) {wardrobe.setAnimal(null);}
			else {wardrobe.setAnimal((Animal) objects.get(animal));}
			wardrobe.center = makeCenterPointFromArrLastTwo(params);
			
			
			DrawableWardrobe dw = new DrawableWardrobe(makeColorFromArr(5, params), wardrobe);
			addVerticesFromArr(8, dw, params);
			gameController.addDrawable(dw);
			wardrobe.setVerts(dw.getVertices());
			objects.put(name, wardrobe);
		}
		
		//set w_pair
		else if(currentLine.startsWith("set w_pair ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			String pair = params[3];
			Wardrobe w = (Wardrobe) objects.get(name);
			w.setPair((Wardrobe) objects.get(pair));
		}
		
		//All field commands
		
		//add neighbour
		else if(currentLine.startsWith("add neighbour ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			String toAdd = params[3];
			Field f = (Field) objects.get(name);
			
			f.addNeigbour((Field) objects.get(toAdd));
		}
		
		//-----------------------------END OF FIELD COMMANDS--------------------------
		
		//-----------------------------------OTHER COMMANDS---------------------
		
		//Timer commands
		
		//create timer
		else if(currentLine.startsWith("create timer ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			Timer t = new Timer();
			t.setName(name);
			objects.put(name, t);
		}
		
		//add tickable
		else if(currentLine.startsWith("add tickable ")) {
			String params[] = currentLine.split(" ");
			String timerName = params[2];
			String tickable = params[3];
			Timer timer = (Timer) objects.get(timerName);
			ITickable it = (ITickable) objects.get(tickable);
			timer.addTickable(it);
		}
		
		// tick tickable
		else if (currentLine.startsWith("tick ")) {
			String params[] = currentLine.split(" ");
			String tickableObj = params[1];
			((ITickable)objects.get(tickableObj)).tick();
		}
		
		//Game commands
		
		//create game
		else if(currentLine.startsWith("create game ")) {
			String params[] = currentLine.split(" ");
			String name = params[2];
			String pnum = params[3];
			int pandaNum;
			try {
				pandaNum = Integer.parseInt(pnum);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("create game pandanum parameter error");
			}
			String timer = params[4];
			Timer t = (Timer) objects.get(timer);
			Game game = new Game();
			game.setTimer(t);
			game.setPandaNum(pandaNum);
			game.setName(name);
			objects.put(name, game);
			gameController.setGame(game);
		}
		
		//add player
		else if(currentLine.startsWith("add player ")) {
			
			
			String params[] = currentLine.split(" ");
			String gameName = params[2];
			String playerName = params[3];
			Game g = (Game) objects.get(gameName);
			Player p = (Player) objects.get(playerName);
			g.addPlayer(p);
			
		}
		
		//Player commands
		
		//create player
		else if(currentLine.startsWith("create player ")) {
			
			
			String params[] = currentLine.split(" ");
			String name = params[2];
			String orangutan = params[3];
			String points = params[4];
			int pts;
			try {
				pts = Integer.parseInt(points);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("create player points parameter error");
			}
			Orangutan o = (Orangutan) objects.get(orangutan);
			Player p = new Player(o);
			p.addPoints(pts);
			p.setName(name);
			objects.put(name, p);
			
		}
		
	}
	
	/******************************************************************/
	
	/**
	 * Returns a Point2D object, made from the arr array last two elements.
	 * @param arr
	 * @return
	 */
	private Point2D makeCenterPointFromArrLastTwo(String[] arr) {
		double x = Double.parseDouble(arr[arr.length-2]);
		double y = Double.parseDouble(arr[arr.length-1]);
		x *= scaleCoords;
		x += TranslateX;
		
		y*=scaleCoords;
		y+=TranslateY;
		y = height - y;
		return new Point2D(x, y);
	}
	
	/**
	 * Returns a Color object made from the arr array, from the given index.
	 * @param ridx start index of the colors
	 * @param arr 
	 * @return
	 */
	private Color makeColorFromArr(int ridx, String[] arr) {
		return new Color(
				Double.parseDouble(arr[ridx++]),    // r 0-1
				Double.parseDouble(arr[ridx++]),    // g 0-1
				Double.parseDouble(arr[ridx++]),    // b 0-1
				1);    
	}
	
	/**
	 * Reads the vertices from the array, transforms them and returns add them to the drawable field.
	 * @param startidx - Start index of the first vertex.
	 * @param df
	 * @param arr
	 */
	private void addVerticesFromArr(int startidx, DrawableField df, String[] arr) {
		for (int i = startidx; i<arr.length-2; i+=2) {
			double x = Double.parseDouble(arr[i]);
			double y = Double.parseDouble(arr[i+1]);
			x *= scaleCoords;
			x += TranslateX;
			
			y*=scaleCoords;
			y+=TranslateY;
			y = height - y;
			
			df.addVertex(new Point2D(x, y));
		}
	}
	
}
