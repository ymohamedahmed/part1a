package uk.ac.cam.cl.gfxintro.crsid.tick1;

public class Plane extends SceneObject {
	
	// Plane constants
	private final double PLANE_KD = 0.9;
	private final double PLANE_KS = 0.0;
	private final double PLANE_ALPHA = 0.0;
	private final double PLANE_REFLECTIVITY = 0.1;

	// A point in the plane
	private Vector3 point;

	// The normal of the plane
	private Vector3 normal;

	public Plane(Vector3 point, Vector3 normal, ColorRGB colour) {
		this.point = point;
		this.normal = normal;
		this.colour = colour;

		this.phong_kD = PLANE_KD;
		this.phong_kS = PLANE_KS;
		this.phong_alpha = PLANE_ALPHA;
		this.reflectivity = PLANE_REFLECTIVITY;
	}
	
	// Intersect this plane with ray
	@Override
	public RaycastHit intersectionWith(Ray ray) {
		// Get ray parameters
		Vector3 O = ray.getOrigin();
		Vector3 D = ray.getDirection();
		
		// Get plane parameters
		Vector3 Q = this.point;
		Vector3 N = this.normal;

		// TODO: Calculate ray parameter s at intersection
		// TODO: If intersection occurs behind camera, return empty RaycastHit;
		//			otherwise return RaycastHit describing point of intersection

		return new RaycastHit(); 
}

	// Get normal to the plane
	@Override
	public Vector3 getNormalAt(Vector3 position) {
		return normal; // normal is the same everywhere on the plane
	}
}
