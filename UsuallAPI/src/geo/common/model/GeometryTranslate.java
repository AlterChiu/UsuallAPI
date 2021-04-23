package geo.common.model;

import java.io.Serializable;

import org.gdal.ogr.Geometry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "coordinate translation for all geometry")
public class GeometryTranslate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("inputEPSG")
	@ApiModelProperty(notes = "input coordinate projection, EPSG", position = 1, value = "inputEPSG", example = "4326", required = true)
	public String inputEPSG;

	@JsonProperty("outputEPSG")
	@ApiModelProperty(notes = "output coordinate projection, EPSG", position = 2, value = "outputEPSG", example = "3826", required = true)
	public String outputEPSG;

	@JsonProperty("geometry")
	@ApiModelProperty(notes = "input geometry", position = 3, value = "geoJson format of geometry", example = "{ \"type\": \"Point\",  \"coordinates\": [30, 10]}", required = true)
	public Object geometry;

	public Geometry getGeometry() {
		try {
			JsonElement json = new JsonParser().parse(this.geometry.toString());
			return Geometry.CreateFromJson(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
