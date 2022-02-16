package it.omicron.parser;

import java.util.ArrayList;
import java.util.List;

public class MenuContent {
	
	private int maxDepth;
    private String version;
    private List<MenuNode> nodes;
    private List<MenuNode> cleanNodes = new ArrayList<MenuNode>();

    public MenuContent() {
        super();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public List<MenuNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuNode> nodes) {
		this.nodes = nodes;
	}
	
	public void setMaxDepth(int maxDepth) {
	    this.maxDepth=maxDepth;
	    
	}

	public int getMaxDepth() {
	    return maxDepth;
	}
	
	public void proccessJson(List<MenuNode> nodes, int depth) {
		if (depth > maxDepth) {
			maxDepth = depth;
		}
		for (MenuNode node : nodes) {
			this.setCleanNodes(node);
			node.setDepth(depth);
			if (node.getNodes() != null && !node.getNodes().isEmpty()) {
				proccessJson(node.getNodes(), depth+1);
			}
		}
	}
	
	public List<MenuNode> getCleanNodes() {
		return cleanNodes;
	}

	public void setCleanNodes(MenuNode node) {
		this.cleanNodes.add(node);
	}
}
