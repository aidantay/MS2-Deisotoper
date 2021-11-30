package model.impl;

import model.PeakInfo;
import model.impl.PeakInfoImpl;

public class PeakInfoImpl implements PeakInfo {

    private double mass;
    private double intensity;

	public PeakInfoImpl(double mass, double intensity) {
		super();
		
		this.mass      = mass;
		this.intensity = intensity;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(intensity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mass);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PeakInfoImpl other = (PeakInfoImpl) obj;
		if (Double.doubleToLongBits(intensity) != Double
				.doubleToLongBits(other.intensity))
			return false;
		if (Double.doubleToLongBits(mass) != Double
				.doubleToLongBits(other.mass))
			return false;
		return true;
	}
	
}
