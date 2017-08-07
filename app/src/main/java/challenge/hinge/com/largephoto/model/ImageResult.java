package challenge.hinge.com.largephoto.model;

import com.google.gson.annotations.SerializedName;

public class ImageResult
{
    @SerializedName("imageURL")
    private String url;
    @SerializedName("imageDescription")
    private String description;
    @SerializedName("imageName")
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ImageResult)) {
            return false;
        }

        return this.url.equals(((ImageResult) obj).getUrl());
    }
}
