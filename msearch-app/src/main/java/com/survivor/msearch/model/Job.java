package com.survivor.msearch.model;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "job", type = "job", shards = 1)
public class Job {

	private long id;

	/** Job title */
	private String title;

	/** Job location; e.g. Dhaka, BD, Houston, Texas */
	private String location;

	/** Job reference as stated at external site */
	private String referenceId;

	/** Job description or specification */
	private String specification;

	/** Job requirements and qualifications needed */
	private String prerequisite;

	/** Job type; e.g. full time, part time etc. */
	private String type;

	/** Job category; e.g. sales, analyst etc. */
	private String category;

	/** Shortname of job advertizing organization */
	private String orgShortName;

	public long getId() {
		return id;
	}

	public void setId(long jobId) {
		this.id = jobId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	@Override
	public String toString() {
		return "Job [jobId=" + id + ", title=" + title + ", location=" + location + ", referenceId=" + referenceId
				+ ", specification=" + specification + ", prerequisite=" + prerequisite + ", type=" + type
				+ ", category=" + category + ", orgShortName=" + orgShortName + "]";
	}
}
