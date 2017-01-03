public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet p) {
		//Calculates r (distance) between planets.
		
		double xD = p.xxPos - xxPos;
		double yD = p.yyPos - yyPos;
		
		return Math.sqrt(xD*xD + yD*yD);

	}

	public double calcForceExertedBy(Planet p) {
		double g = 6.67 * Math.pow(10, -11);
		return (g * mass * p.mass)/Math.pow(this.calcDistance(p),2);

	}
	public double calcForceExertedByX(Planet p) {
		double xD = p.xxPos - this.xxPos;
		double f = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		return f*xD/r;
	}
	public double calcForceExertedByY(Planet p) {
		double yD = p.yyPos - this.yyPos;
		double f = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		return f*yD/r;
	}

	public double calcNetForceExertedByX(Planet[] p) {
		double totalForceX = 0;
		for(int i = 0; i < p.length; i++) {
			if(!(this.equals(p[i]))) {
				totalForceX += this.calcForceExertedByX(p[i]);
			}
		}
		return totalForceX;
	}
	public double calcNetForceExertedByY(Planet[] p) {
		double totalForceY = 0;

		for(int i = 0; i < p.length; i++) {
			if(!(this.equals(p[i]))) {
				totalForceY += this.calcForceExertedByY(p[i]);
			}
		}
		return totalForceY;
	}

	public void update(double dt, double xF, double yF) {
		double aX = xF/mass;
		double aY = yF/mass;
		double xxVelNew = xxVel + dt*aX;
		double yyVelNew = yyVel + dt*aY;
		double xxPosNew = xxPos + dt*xxVelNew;
		double yyPosNew = yyPos + dt*yyVelNew;
		this.xxVel = xxVelNew;
		this.yyVel = yyVelNew;
		this.xxPos = xxPosNew;
		this.yyPos = yyPosNew;

	}

	public void draw() {
		String picturePath = "./images/" + imgFileName;
		StdDraw.picture(xxPos,yyPos,picturePath);
	}


}