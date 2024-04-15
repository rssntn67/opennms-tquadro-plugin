package org.opennms.tquadro.client.v1.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AssetOpenNMS
 */

public class AssetOpenNMS {
  @JsonProperty("assetId")
  private Integer assetId = null;

  @JsonProperty("ipAddress")
  private String ipAddress = null;

  @JsonProperty("hostname")
  private String hostname = null;

  @JsonProperty("nameSNMP")
  private String nameSNMP = null;

  @JsonProperty("sysObjectIdSNMP")
  private String sysObjectIdSNMP = null;

  @JsonProperty("locationSNMP")
  private String locationSNMP = null;

  @JsonProperty("contactSNMP")
  private String contactSNMP = null;

  @JsonProperty("descriptionSNMP")
  private String descriptionSNMP = null;

  @JsonProperty("macroarea")
  private String area = null;

  public AssetOpenNMS assetId(Integer assetId) {
    this.assetId = assetId;
    return this;
  }

   /**
   * Get assetId
   * @return assetId
  **/
  public Integer getAssetId() {
    return assetId;
  }

  public void setAssetId(Integer assetId) {
    this.assetId = assetId;
  }

  public AssetOpenNMS ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

   /**
   * Get ipAddress
   * @return ipAddress
  **/
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public AssetOpenNMS hostname(String hostname) {
    this.hostname = hostname;
    return this;
  }

   /**
   * Get hostname
   * @return hostname
  **/
  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public AssetOpenNMS nameSNMP(String nameSNMP) {
    this.nameSNMP = nameSNMP;
    return this;
  }

   /**
   * Get nameSNMP
   * @return nameSNMP
  **/
  public String getNameSNMP() {
    return nameSNMP;
  }

  public void setNameSNMP(String nameSNMP) {
    this.nameSNMP = nameSNMP;
  }

  public AssetOpenNMS sysObjectIdSNMP(String sysObjectIdSNMP) {
    this.sysObjectIdSNMP = sysObjectIdSNMP;
    return this;
  }

   /**
   * Get sysObjectIdSNMP
   * @return sysObjectIdSNMP
  **/
  public String getSysObjectIdSNMP() {
    return sysObjectIdSNMP;
  }

  public void setSysObjectIdSNMP(String sysObjectIdSNMP) {
    this.sysObjectIdSNMP = sysObjectIdSNMP;
  }

  public AssetOpenNMS locationSNMP(String locationSNMP) {
    this.locationSNMP = locationSNMP;
    return this;
  }

   /**
   * Get locationSNMP
   * @return locationSNMP
  **/
  public String getLocationSNMP() {
    return locationSNMP;
  }

  public void setLocationSNMP(String locationSNMP) {
    this.locationSNMP = locationSNMP;
  }

  public AssetOpenNMS contactSNMP(String contactSNMP) {
    this.contactSNMP = contactSNMP;
    return this;
  }

   /**
   * Get contactSNMP
   * @return contactSNMP
  **/
  public String getContactSNMP() {
    return contactSNMP;
  }

  public void setContactSNMP(String contactSNMP) {
    this.contactSNMP = contactSNMP;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public AssetOpenNMS descriptionSNMP(String descriptionSNMP) {
    this.descriptionSNMP = descriptionSNMP;
    return this;
  }

   /**
   * Get descriptionSNMP
   * @return descriptionSNMP
  **/
  public String getDescriptionSNMP() {
    return descriptionSNMP;
  }

  public void setDescriptionSNMP(String descriptionSNMP) {
    this.descriptionSNMP = descriptionSNMP;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssetOpenNMS assetOpenNMS = (AssetOpenNMS) o;
    return Objects.equals(this.assetId, assetOpenNMS.assetId) &&
        Objects.equals(this.ipAddress, assetOpenNMS.ipAddress) &&
        Objects.equals(this.hostname, assetOpenNMS.hostname) &&
        Objects.equals(this.nameSNMP, assetOpenNMS.nameSNMP) &&
        Objects.equals(this.sysObjectIdSNMP, assetOpenNMS.sysObjectIdSNMP) &&
        Objects.equals(this.locationSNMP, assetOpenNMS.locationSNMP) &&
        Objects.equals(this.contactSNMP, assetOpenNMS.contactSNMP) &&
        Objects.equals(this.area, assetOpenNMS.area) &&
        Objects.equals(this.descriptionSNMP, assetOpenNMS.descriptionSNMP);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assetId, ipAddress, hostname, nameSNMP, sysObjectIdSNMP, locationSNMP, contactSNMP, descriptionSNMP, area);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssetOpenNMS {\n");
    
    sb.append("    assetId: ").append(toIndentedString(assetId)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    hostname: ").append(toIndentedString(hostname)).append("\n");
    sb.append("    nameSNMP: ").append(toIndentedString(nameSNMP)).append("\n");
    sb.append("    sysObjectIdSNMP: ").append(toIndentedString(sysObjectIdSNMP)).append("\n");
    sb.append("    locationSNMP: ").append(toIndentedString(locationSNMP)).append("\n");
    sb.append("    contactSNMP: ").append(toIndentedString(contactSNMP)).append("\n");
    sb.append("    descriptionSNMP: ").append(toIndentedString(descriptionSNMP)).append("\n");
    sb.append("    area: ").append(toIndentedString(area)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
