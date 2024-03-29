package mosip.perf.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class IndividualDemographicDedupePKEntity.
 *
 * @author Girish Yarru
 */
@Embeddable
public class IndividualDemographicDedupePKEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The reg id. */
	@Column(name = "reg_id", nullable = false)
	private String regId;

	/** The lang code. */
	@Column(name = "lang_code", nullable = false)
	private String langCode;

	/**
	 * Instantiates a new individual demographic dedupe PK entity.
	 */
	public IndividualDemographicDedupePKEntity() {
		super();
	}

	/**
	 * Gets the reg id.
	 *
	 * @return the reg id
	 */
	public String getRegId() {
		return this.regId;
	}

	/**
	 * Sets the reg id.
	 *
	 * @param regId the new reg id
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * Gets the lang code.
	 *
	 * @return the lang code
	 */
	public String getLangCode() {
		return this.langCode;
	}

	/**
	 * Sets the lang code.
	 *
	 * @param langCode the new lang code
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IndividualDemographicDedupePKEntity)) {
			return false;
		}
		IndividualDemographicDedupePKEntity castOther = (IndividualDemographicDedupePKEntity) other;
		return this.regId.equals(castOther.regId) && this.langCode.equals(castOther.langCode);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.regId.hashCode();
		hash = hash * prime + this.langCode.hashCode();

		return hash;
	}
}