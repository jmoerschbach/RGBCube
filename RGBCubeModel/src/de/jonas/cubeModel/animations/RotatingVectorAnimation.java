package de.jonas.cubeModel.animations;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import de.jonas.cubeModel.Cube;

public class RotatingVectorAnimation extends Animation {

	private int state = 0;
	Vector2D vector = new Vector2D(3, 0);
	Vector2D startPoint = new Vector2D(0, 3);
	Vector2D endPoint;

	public RotatingVectorAnimation(Cube cubes) {
		super(cubes);
		turnLayerOn(7);
	}

	private void rotateVectorBy(double alpha) {
		double x = Math.cos(Math.toRadians(alpha)) * vector.getX() + Math.sin(Math.toRadians(alpha)) * vector.getY();
		double y = -Math.sin(Math.toRadians(alpha)) * vector.getX() + Math.cos(Math.toRadians(alpha)) * vector.getY();
		// double x = 0 * vector.getX() + 1 * vector.getY();
		// double y = -1 * vector.getX() + 0 * vector.getY();
		vector = new Vector2D(x, y);
		endPoint = new Vector2D(startPoint.getX() + vector.getX(), startPoint.getY() + vector.getY());
	}

	public void animate1000ms() {

		rotateVectorBy(45);
		lightLeds();
		setLedBlue(7, 1);
	}

	private void lightLeds() {
		clearAllLeds();
		System.out.println(startPoint.getX() + ":" + endPoint.getY());
		setLedWhite((int) Math.round(startPoint.getY()), (int) Math.round(startPoint.getX()));
		setLedWhite((int) Math.round(endPoint.getY()), (int) Math.round(endPoint.getX()));
		if (polStelle()) {
			int base = startPoint.getY() < endPoint.getY() ? (int) Math.round(startPoint.getY())
					: (int) Math.round(endPoint.getY());
			int stop = startPoint.getY() < endPoint.getY() ? (int) Math.round(endPoint.getY())
					: (int) Math.round(startPoint.getY());
			for (int i = base; i < stop; i++) {
				setLedWhite(i, (int) Math.round(startPoint.getX()));
			}
		} else {
			int start = startPoint.getX() < endPoint.getX() ? (int) Math.round(startPoint.getX())
					: (int) Math.round(endPoint.getX());
			int stop = startPoint.getX() < endPoint.getX() ? (int) Math.round(endPoint.getX())
					: (int) Math.round(startPoint.getX());
			System.out.println(start + ":" + stop);
			for (int i = start; i < stop; i++) {
				System.out.print(i+","+calcY(i)+" ");
				setLedWhite(calcY(i), i);
			}
			System.out.println();

		}
	}

	private boolean polStelle() {
		return Math.round(startPoint.getX()) - Math.round(endPoint.getX()) == 0;
	}

	private int calcY(int i) {
		double y = startPoint.getY() + i * (endPoint.getY() - startPoint.getY());
		return (int) Math.round(y);
	}
	
	private int calcX(int i) {
		double x = startPoint.getX() + i * (endPoint.getX() - startPoint.getX());
		return (int) Math.round(x);
	}

	private void printVector() {
		System.out.print(vector);
		System.out.println();

	}

	@Override
	public String getAnimationName() {
		return "rotierender Vektor";
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void loop() {
		// TODO Auto-generated method stub
		
	}

}
