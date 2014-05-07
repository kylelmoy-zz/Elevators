import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import simulationObjects.Building;
import simulationObjects.ElevatorCar;
import simulationObjects.ElevatorController;



public class Main {
	private static Building building;
	public static void main(String[] args) throws Exception{
		if (args.length != 1) {
			throw new Error("Usage: Main.java <input case file>");
		}
		Scanner sc = new Scanner(new File(args[0]));
		int floors, time, elevators, elevatorSpeed, doorSpeed, timeOutSpeed;
		floors = sc.nextInt();
		time = sc.nextInt();
		elevators = sc.nextInt();
		elevatorSpeed = sc.nextInt();
		doorSpeed = sc.nextInt();
		timeOutSpeed = sc.nextInt();
		Building.setFloors(floors);
		ElevatorController.setElevators(elevators);
		ElevatorController.setElevatorSpeed(elevatorSpeed);
		ElevatorController.setTimeOutSpeed(timeOutSpeed);
		ElevatorController.setDoorSpeed(doorSpeed);
		building = Building.getInstance();
		//Under normal circumstances these requests shouldn't ever be made outside the Occupants and the buttons that interface with the controller
		//So, I had to take some liberties with the code.
		ElevatorController ec = ElevatorController.getInstance();
		ArrayList<ElevatorCar> eCars = ec.getElevatorCarList();
		
		int nextEvent = sc.nextInt();
		for (int i = 0; i < time; i ++) {
			building.step();
			if (i == nextEvent) {
				int elevator, floor, direction;
				elevator = sc.nextInt();
				floor = sc.nextInt();
				direction = sc.nextInt();
				if (direction == 0) {
					eCars.get(elevator).floorRequest(floor);
				} else {
					eCars.get(elevator).floorRequest(floor,direction>0?true:false);
				}
				nextEvent = sc.nextInt();
			}
		}
		/*
		step(1000);
		//ec.request(11, true);
		eCars.get(0).floorRequest(10, true); //Controller request (floor, direction)
		step(1000);
		//ec.request(14, true);
		eCars.get(1).floorRequest(12, true);
		step(1000);
		//ec.request(13, true);
		eCars.get(1).floorRequest(13, true);
		step(1000);
		//ec.request(15, true);
		eCars.get(1).floorRequest(14, true);
		step(35000);
		eCars.get(2).floorRequest(4, true);
		step(2000);
		eCars.get(2).floorRequest(15, true);
		step(2000);
		eCars.get(2).floorRequest(0); //Internal button request (floor)
		step(10000);
		eCars.get(2).floorRequest(1);
		step(1000);
		eCars.get(2).floorRequest(2);
		step(1000);
		eCars.get(2).floorRequest(4);
		step(35000);
		*/

		sc.close();
	}
	private static void step(int step) {
		for (int i = 0; i < step; i ++) {
			building.step();
			//if (Building.currentTime() % 100 == 0)
				//System.out.println(Building.currentTime());
		}
	}
}
