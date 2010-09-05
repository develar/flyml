package org.flyti.flyml;

import org.yaml.snakeyaml.composer.ComposerException;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.events.MappingStartEvent;
import org.yaml.snakeyaml.events.NodeEvent;
import org.yaml.snakeyaml.events.ScalarEvent;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.resolver.Resolver;

import java.util.ArrayList;

public class Composer {
  private Parser parser;

  private final Resolver resolver;
  private final Builder builder;

  public Composer(Resolver resolver, Builder builder) {
    this.resolver = resolver;
    this.builder = builder;
//    this.anchors = new HashMap<String, Node>();
//    this.recursiveNodes = new HashSet<Node>();
  }

  public void setParser(Parser parser) {
    this.parser = parser;
  }

  public void compose() {
    // Drop the STREAM-START event.
    parser.getEvent();

    if (!parser.checkEvent(Event.ID.StreamEnd)) {
      composeDocument();
    }

    // Ensure that the stream contains no more documents.
    if (!parser.checkEvent(Event.ID.StreamEnd)) {
//      Event event = parser.getEvent();
//      throw new ComposerException("expected a single document in the stream", document
//              .getStartMark(), "but found another document", event.getStartMark());
    }
  }

  private void composeDocument() {
    // Drop the DOCUMENT-START event.
    parser.getEvent();
    // Compose the root node.
    composeNode();
    // Drop the DOCUMENT-END event.
    parser.getEvent();
//    this.anchors.clear();
//    recursiveNodes.clear();
//    return node;
  }

  private void composeNode() {
//    recursiveNodes.add(parent);
    if (parser.checkEvent(Event.ID.Alias)) {
      // todo support alias
//      AliasEvent event = (AliasEvent) parser.getEvent();
//      String anchor = event.getAnchor();
//      if (!anchors.containsKey(anchor)) {
//        throw new ComposerException(null, null, "found undefined alias " + anchor, event
//                .getStartMark());
//      }
//      Node result = (Node) anchors.get(anchor);
//      if (recursiveNodes.remove(result)) {
//        result.setTwoStepsConstruction(true);
//      }
//      return result;
    }

    NodeEvent event = (NodeEvent) parser.peekEvent();
    String anchor = event.getAnchor();
//    if (anchor != null && anchors.containsKey(anchor)) {
//      throw new ComposerException("found duplicate anchor " + anchor + "; first occurence",
//              this.anchors.get(anchor).getStartMark(), "second occurence", event
//              .getStartMark());
//    }
    // resolver.descendResolver(parent, index);
    Node node = null;
    if (parser.checkEvent(Event.ID.Scalar)) {
//      node = composeScalarNode(anchor);
      composeScalarNode(anchor);
    }
    else if (parser.checkEvent(Event.ID.SequenceStart)) {
//      node = composeSequenceNode(anchor);
    }
    else {
//      node = composeMappingNode(anchor);
      composeMappingNode(anchor);
    }
    // resolver.ascendResolver();
//    recursiveNodes.remove(parent);
//    return null;
  }

  private void composeScalarNode(String anchor) {
    ScalarEvent event = (ScalarEvent) parser.getEvent();
    builder.setStringValue(event.getValue());
//    String tag = event.getTag();
//    boolean resolved = false;
//    Tag nodeTag;
//    if (tag == null || tag.equals("!")) {
//      nodeTag = resolver.resolve(NodeId.scalar, event.getValue(), event.getImplicit().isFirst());
//      resolved = true;
//    }
//    else {
//      nodeTag = new Tag(tag);
//    }
//    Node node = new ScalarNode(nodeTag, resolved, event.getValue(), event.getStartMark(), event.getEndMark(), event.getStyle());
//    if (anchor != null) {
////      anchors.put(anchor, node);
//    }
//    return node;
  }

  private void composeMappingNode(String anchor) {
    if (anchor != null) {
//      anchors.put(anchor, node);
    }

    builder.startMap();
    while (!parser.checkEvent(Event.ID.MappingEnd)) {
      String key = composeMapKey();
      char firstChar = key.charAt(0);
      if (firstChar <= 'Z' && firstChar >= 'A') {
        builder.createObject(key);
      }
      else {
        builder.setProperty(key);
      }

      composeNode();
//      Node itemValue = composeNode(node);
//      node.getValue().add(new NodeTuple(itemKey, itemValue));
    }
    builder.endMap();

//    return node;
  }

  private String composeMapKey() {
    Event event = parser.getEvent();
    if (event instanceof ScalarEvent) {
      return ((ScalarEvent) event).getValue();
    }
    else {
      throw new ComposerException(null, null, "Map Key must be scalar", parser.peekEvent().getStartMark());
    }
  }
}
