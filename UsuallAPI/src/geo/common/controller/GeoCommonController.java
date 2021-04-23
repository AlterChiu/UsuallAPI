package geo.common.controller;

import java.io.IOException;

import org.gdal.ogr.Geometry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import geo.common.model.GeometryTranslate;
import geo.gdal.GdalGlobal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "GeoCommonTool", description = "GIS 基本工具")
@RestController
@RequestMapping("/api/geoCommonTool")
public class GeoCommonController {

	@ApiOperation(value = "Geojson CoordinateTranslte", notes = "Geojson座標轉換")
	@PostMapping(value = "/translate/geometry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public String getGeometryTranslate(@RequestBody(required = true) GeometryTranslate coordinateTranslate)
			throws IOException {
		int inputEPSG = Integer.parseInt(coordinateTranslate.inputEPSG);
		int outputEPSG = Integer.parseInt(coordinateTranslate.outputEPSG);

		try {
			Geometry geo = coordinateTranslate.getGeometry();
			return GdalGlobal.GeometryTranslator(geo, inputEPSG, outputEPSG).ExportToJson();
		} catch (Exception e) {
			return "{" + e.getMessage() + "}";
		}
	}

	@ApiOperation(value = "pointCoordinateTranslte", notes = "點位座標轉換")
	@GetMapping(value = "/translate/point", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getPointCoordinateTranslate(
			@ApiParam(value = "coordinateX", type = "Double", example = "121", required = true) @RequestParam double x,
			@ApiParam(value = "coordinateY", type = "Double", example = "25", required = true) @RequestParam double y,
			@ApiParam(value = "EPSG for request coordination", type = "Int", example = "4326", required = true) @RequestParam int inEPSG,
			@ApiParam(value = "EPSG for response coordination", type = "Int", example = "3826", required = true) @RequestParam int outEPSG) {

		// try coordinate translate
		try {
			double[] translatedPoint = GdalGlobal.CoordinateTranslator(x, y, inEPSG, outEPSG);
			JsonObject outJson = new JsonObject();
			outJson.addProperty("x", translatedPoint[0]);
			outJson.addProperty("y", translatedPoint[1]);
			return outJson.toString();

			// return exception
		} catch (Exception e) {
			return e.toString();
		}
	}

	@ApiOperation(value = "test api", notes = "測試連線")
	@GetMapping(value = "/api/geoCommonTool/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getPointCoordinateTranslate() {
		return "{return : \"hellow\"}";
	}
}
