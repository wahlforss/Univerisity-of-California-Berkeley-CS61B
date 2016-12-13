public class NBody {

	public static void main(String[] args) {
		double t = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Planet[] planetArray = readPlanets(filename);
		double radius = readRadius(filename);
		double time = 0;

		while(time != t) {
			double[] xForces = new double[planetArray.length];
			double[] yForces = new double[planetArray.length];
			
			for(int i=0; i < planetArray.length;i++) {
				xForces[i] = planetArray[i].calcNetForceExertedByX(planetArray);
				yForces[i] = planetArray[i].calcNetForceExertedByY(planetArray);
			}
			System.out.println(xForces[1]);

			for(int i=0; i < planetArray.length;i++) {
				planetArray[i].update(dt,xForces[0],yForces[0]);
			}
			drawbackground(radius);
			//Draws all planets
			for(int i=0;i < planetArray.length; i++) {
			planetArray[i].draw();
			}
			StdDraw.show(10);
			time += dt;	
		}


	}

	public static void drawbackground(double r) {
		String background = "./images/starfield.jpg";
		StdDraw.setScale(-r,r);
		StdDraw.picture(0,0,background);
	}

	public static double readRadius(String path) {
		In in = new In(path);
		in.readLine();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String path) {
		In in = new In(path);
		int length = in.readInt();
		in.readLine();
		in.readLine();
		Planet[] planetArray = new Planet[length];
		int index = 0;
		while(index < length) {
			planetArray[index] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
			index += 1;
		}
		return planetArray;
	}


}