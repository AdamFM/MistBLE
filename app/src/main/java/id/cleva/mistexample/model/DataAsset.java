
package id.cleva.mistexample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAsset implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("site_id")
    @Expose
    private String siteId;
    @SerializedName("org_id")
    @Expose
    private String orgId;
    @SerializedName("created_time")
    @Expose
    private int createdTime;
    @SerializedName("modified_time")
    @Expose
    private int modifiedTime;
    @SerializedName("map_id")
    @Expose
    private String mapId;
    @SerializedName("tag_id")
    @Expose
    private String tagId;
    @SerializedName("mac")
    @Expose
    private String mac;
    @SerializedName("eddystone_uid_namespace")
    @Expose
    private String eddystoneUidNamespace;
    @SerializedName("eddystone_uid_instance")
    @Expose
    private String eddystoneUidInstance;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("manufacture")
    @Expose
    private String manufacture;
    @SerializedName("by")
    @Expose
    private String by;
    @SerializedName("last_seen")
    @Expose
    private double lastSeen;
    @SerializedName("x")
    @Expose
    private double x;
    @SerializedName("y")
    @Expose
    private double y;
    @SerializedName("_ttl")
    @Expose
    private int Ttl;
    @SerializedName("_id")
    @Expose
    private String _Id;
    public final static Parcelable.Creator<DataAsset> CREATOR = new Creator<DataAsset>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DataAsset createFromParcel(Parcel in) {
            DataAsset instance = new DataAsset();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.siteId = ((String) in.readValue((String.class.getClassLoader())));
            instance.orgId = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.modifiedTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.mapId = ((String) in.readValue((String.class.getClassLoader())));
            instance.tagId = ((String) in.readValue((String.class.getClassLoader())));
            instance.mac = ((String) in.readValue((String.class.getClassLoader())));
            instance.eddystoneUidNamespace = ((String) in.readValue((String.class.getClassLoader())));
            instance.eddystoneUidInstance = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceName = ((String) in.readValue((String.class.getClassLoader())));
            instance.manufacture = ((String) in.readValue((String.class.getClassLoader())));
            instance.by = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastSeen = ((double) in.readValue((double.class.getClassLoader())));
            instance.x = ((double) in.readValue((double.class.getClassLoader())));
            instance.y = ((double) in.readValue((double.class.getClassLoader())));
            instance.Ttl = ((int) in.readValue((int.class.getClassLoader())));
            instance._Id = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DataAsset[] newArray(int size) {
            return (new DataAsset[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * 
     * @param siteId
     *     The site_id
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * 
     * @return
     *     The orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 
     * @param orgId
     *     The org_id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 
     * @return
     *     The createdTime
     */
    public int getCreatedTime() {
        return createdTime;
    }

    /**
     * 
     * @param createdTime
     *     The created_time
     */
    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 
     * @return
     *     The modifiedTime
     */
    public int getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 
     * @param modifiedTime
     *     The modified_time
     */
    public void setModifiedTime(int modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 
     * @return
     *     The mapId
     */
    public String getMapId() {
        return mapId;
    }

    /**
     * 
     * @param mapId
     *     The map_id
     */
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    /**
     * 
     * @return
     *     The tagId
     */
    public String getTagId() {
        return tagId;
    }

    /**
     * 
     * @param tagId
     *     The tag_id
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     * 
     * @return
     *     The mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * 
     * @param mac
     *     The mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 
     * @return
     *     The eddystoneUidNamespace
     */
    public String getEddystoneUidNamespace() {
        return eddystoneUidNamespace;
    }

    /**
     * 
     * @param eddystoneUidNamespace
     *     The eddystone_uid_namespace
     */
    public void setEddystoneUidNamespace(String eddystoneUidNamespace) {
        this.eddystoneUidNamespace = eddystoneUidNamespace;
    }

    /**
     * 
     * @return
     *     The eddystoneUidInstance
     */
    public String getEddystoneUidInstance() {
        return eddystoneUidInstance;
    }

    /**
     * 
     * @param eddystoneUidInstance
     *     The eddystone_uid_instance
     */
    public void setEddystoneUidInstance(String eddystoneUidInstance) {
        this.eddystoneUidInstance = eddystoneUidInstance;
    }

    /**
     * 
     * @return
     *     The deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 
     * @param deviceName
     *     The device_name
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 
     * @return
     *     The manufacture
     */
    public String getManufacture() {
        return manufacture;
    }

    /**
     * 
     * @param manufacture
     *     The manufacture
     */
    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    /**
     * 
     * @return
     *     The by
     */
    public String getBy() {
        return by;
    }

    /**
     * 
     * @param by
     *     The by
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * 
     * @return
     *     The lastSeen
     */
    public double getLastSeen() {
        return lastSeen;
    }

    /**
     * 
     * @param lastSeen
     *     The last_seen
     */
    public void setLastSeen(double lastSeen) {
        this.lastSeen = lastSeen;
    }

    /**
     * 
     * @return
     *     The x
     */
    public double getX() {
        return x;
    }

    /**
     * 
     * @param x
     *     The x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * 
     * @return
     *     The y
     */
    public double getY() {
        return y;
    }

    /**
     * 
     * @param y
     *     The y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * 
     * @return
     *     The Ttl
     */
    public int getTtl() {
        return Ttl;
    }

    /**
     * 
     * @param Ttl
     *     The _ttl
     */
    public void setTtl(int Ttl) {
        this.Ttl = Ttl;
    }

    /**
     * 
     * @return
     *     The Id
     */
    public String get_Id() {
        return _Id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    public void set_Id(String Id) {
        this._Id = Id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
        dest.writeValue(siteId);
        dest.writeValue(orgId);
        dest.writeValue(createdTime);
        dest.writeValue(modifiedTime);
        dest.writeValue(mapId);
        dest.writeValue(tagId);
        dest.writeValue(mac);
        dest.writeValue(eddystoneUidNamespace);
        dest.writeValue(eddystoneUidInstance);
        dest.writeValue(deviceName);
        dest.writeValue(manufacture);
        dest.writeValue(by);
        dest.writeValue(lastSeen);
        dest.writeValue(x);
        dest.writeValue(y);
        dest.writeValue(Ttl);
        dest.writeValue(_Id);
    }

    public int describeContents() {
        return  0;
    }

}
