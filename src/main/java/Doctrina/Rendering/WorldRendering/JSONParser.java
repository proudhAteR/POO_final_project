package Doctrina.Rendering.WorldRendering;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Doctrina.Rendering.ResourcesManager;
import city_cleaner.World;

public class JSONParser {
    private static JSONParser instance;
    private static ObjectMapper mapper;

    public static JSONParser getInstance() {
        mapper = new ObjectMapper();
        if (instance == null) {
            instance = new JSONParser();
        }
        return instance;
    }

    public World getWorld(String path) throws FileNotFoundException, IOException, URISyntaxException {
        String json = ResourcesManager.readFile(path);
        JsonNode node;
        try {
            node = mapper.readTree(json);
            return new World(
                node.get("compressionlevel").asInt(),
                node.get("height").asInt(),
                node.get("infinite").asBoolean(),
                getLayers(node),
                node.get("nextlayerid").asInt(),
                node.get("nextobjectid").asInt(),
                node.get("orientation").asText(),
                node.get("renderorder").asText(),
                node.get("tiledversion").asText(),
                node.get("tileheight").asInt(),
                generateTilesets(node),
                node.get("tilewidth").asInt(),
                node.get("type").asText(),
                node.get("version").asText(),
                node.get("width").asInt()
        );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
        
    }

    private List<Layer> getLayers(JsonNode node) {
        JsonNode layersNode = node.get("layers");
        List<Layer> list = new ArrayList<>();

        if (layersNode.isArray()) {
            for (JsonNode n : layersNode) {
                Layer object = new Layer(
                        generateChuncks(n), n.get("height").asInt(), n.get("id").asInt(), n.get("name").asText(),
                        n.get("opacity").asInt(), n.get("startx").asInt(),
                        n.get("starty").asInt(), n.get("type").asText(),
                        n.get("visible").asBoolean(), n.get("width").asInt(),
                        n.get("x").asInt(), n.get("y").asInt());
                list.add(object);
            }
        }
        return null;
    }

    private List<Tileset> generateTilesets(JsonNode node) {
        JsonNode tilesetsNode = node.get("tilesets");
        List<Tileset> list = new ArrayList<>();

        if (tilesetsNode.isArray()) {
            for (JsonNode n : tilesetsNode) {
                Tileset object = new Tileset(
                        n.get("columns").asInt(), n.get("firstgid").asInt(), n.get("image").asText(),
                        n.get("imageheight").asInt(), n.get("imagewidth").asInt(), n.get("margin").asInt(),
                        n.get("name").asText(),
                        n.get("spacing").asInt(), n.get("tilecount").asInt(), n.get("tileheight").asInt(),
                        n.get("tilewidth").asInt());
                list.add(object);
            }
            return list;
        }

        return null;
    }

    private List<Chunk> generateChuncks(JsonNode node) {
        JsonNode chunksNode = node.get("chunks");
        List<Chunk> list = new ArrayList<>();
        if (chunksNode.isArray()) {
            for (JsonNode n : chunksNode) {
                JsonNode arrayNode = n.get("data");
                List<Integer> datas = new ArrayList<>();

                for (JsonNode o : arrayNode) {
                    datas.add(o.asInt());
                }
                Chunk object = new Chunk(
                        datas, n.get("height").asInt(),
                        n.get("width").asInt(),
                        n.get("x").asInt(), n.get("y").asInt());

                list.add(object);
            }

        }

        return null;
    }
}
