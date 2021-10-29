
package id.cleva.mistexample.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.cleva.mistexample.R;


public class DataMaps implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("width_m")
    @Expose
    private double widthM;
    @SerializedName("height_m")
    @Expose
    private double heightM;
    @SerializedName("origin_x")
    @Expose
    private double originX;
    @SerializedName("origin_y")
    @Expose
    private double originY;
    @SerializedName("ppm")
    @Expose
    private double ppm;
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
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    // important code for loading image here
    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
//                .setDefaultRequestOptions(new RequestOptions()
//                        .circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.ic_baseline_dvr)
                .into(imageView);
    }

    public final static Parcelable.Creator<DataMaps> CREATOR = new Creator<DataMaps>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DataMaps createFromParcel(Parcel in) {
            DataMaps instance = new DataMaps();
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.width = ((int) in.readValue((int.class.getClassLoader())));
            instance.height = ((int) in.readValue((int.class.getClassLoader())));
            instance.widthM = ((double) in.readValue((double.class.getClassLoader())));
            instance.heightM = ((double) in.readValue((double.class.getClassLoader())));
            instance.originX = ((double) in.readValue((double.class.getClassLoader())));
            instance.originY = ((double) in.readValue((double.class.getClassLoader())));
            instance.ppm = ((double) in.readValue((double.class.getClassLoader())));
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.siteId = ((String) in.readValue((String.class.getClassLoader())));
            instance.orgId = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.modifiedTime = ((int) in.readValue((int.class.getClassLoader())));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public DataMaps[] newArray(int size) {
            return (new DataMaps[size]);
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
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The widthM
     */
    public double getWidthM() {
        return widthM;
    }

    /**
     * 
     * @param widthM
     *     The width_m
     */
    public void setWidthM(double widthM) {
        this.widthM = widthM;
    }

    /**
     * 
     * @return
     *     The heightM
     */
    public double getHeightM() {
        return heightM;
    }

    /**
     * 
     * @param heightM
     *     The height_m
     */
    public void setHeightM(double heightM) {
        this.heightM = heightM;
    }

    /**
     * 
     * @return
     *     The originX
     */
    public double getOriginX() {
        return originX;
    }

    /**
     * 
     * @param originX
     *     The origin_x
     */
    public void setOriginX(double originX) {
        this.originX = originX;
    }

    /**
     * 
     * @return
     *     The originY
     */
    public double getOriginY() {
        return originY;
    }

    /**
     * 
     * @param originY
     *     The origin_y
     */
    public void setOriginY(double originY) {
        this.originY = originY;
    }

    /**
     * 
     * @return
     *     The ppm
     */
    public double getPpm() {
        return ppm;
    }

    /**
     * 
     * @param ppm
     *     The ppm
     */
    public void setPpm(double ppm) {
        this.ppm = ppm;
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
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * 
     * @param thumbnailUrl
     *     The thumbnail_url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(type);
        dest.writeValue(width);
        dest.writeValue(height);
        dest.writeValue(widthM);
        dest.writeValue(heightM);
        dest.writeValue(originX);
        dest.writeValue(originY);
        dest.writeValue(ppm);
        dest.writeValue(id);
        dest.writeValue(siteId);
        dest.writeValue(orgId);
        dest.writeValue(createdTime);
        dest.writeValue(modifiedTime);
        dest.writeValue(url);
        dest.writeValue(thumbnailUrl);
    }

    public int describeContents() {
        return  0;
    }

}
