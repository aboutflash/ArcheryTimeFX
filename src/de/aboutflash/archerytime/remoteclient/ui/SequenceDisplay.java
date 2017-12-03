package de.aboutflash.archerytime.remoteclient.ui;

import de.aboutflash.archerytime.model.ScreenState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.SVGPath;

/**
 * Class
 *
 * @author falk@aboutflash.de on 30.11.2017.
 */
public class SequenceDisplay extends StackPane {
  private static final String DEFAULT_STYLE_CLASS = "sequence-display";
  private static final String SEQUENCE_AB_STYLE_CLASS = "sequence-display__ab";
  private static final String SEQUENCE_CD_STYLE_CLASS = "sequence-display__cd";

  private static final PseudoClass ACTIVE = PseudoClass.getPseudoClass("active");

  private final ObjectProperty<ScreenState.Sequence> sequence = new SimpleObjectProperty<>(ScreenState.Sequence.AB);
  private Node firstSequenceIndicator;
  private Node secondSequenceIndicator;

  public SequenceDisplay() {
    getStyleClass().add(DEFAULT_STYLE_CLASS);

    sequence.addListener((observable, oldValue, newValue) -> {
      if (newValue == null) {
        getChildren().clear();
        return;
      }

      switch (newValue) {
        case A:
          showSequenceA();
          break;
        case AB:
          showSequenceAB();
          break;
        case B:
          showSequenceB();
          break;
        case CD:
          showSequenceCD();
          break;
      }
    });
  }

  private void showSequenceA() {
    showSequence(getSequenceA(), getSequenceB());
    firstSequenceIndicator.pseudoClassStateChanged(ACTIVE, true);
  }

  private void showSequenceB() {
    showSequence(getSequenceA(), getSequenceB());
    secondSequenceIndicator.pseudoClassStateChanged(ACTIVE, true);
  }

  private void showSequenceAB() {
    showSequence(getSequenceAB(), getSequenceCD());
    firstSequenceIndicator.pseudoClassStateChanged(ACTIVE, true);
  }

  private void showSequenceCD() {
    showSequence(getSequenceAB(), getSequenceCD());
    secondSequenceIndicator.pseudoClassStateChanged(ACTIVE, true);
  }

  private void showSequence(Node first, Node second) {
    getChildren().clear();
    firstSequenceIndicator = first;
    firstSequenceIndicator.getStyleClass().add(SEQUENCE_AB_STYLE_CLASS);

    secondSequenceIndicator = second;
    secondSequenceIndicator.getStyleClass().add(SEQUENCE_CD_STYLE_CLASS);
    getChildren().addAll(firstSequenceIndicator, secondSequenceIndicator);
  }

  private Node getSequenceA() {
    return createCombinedShape("M 42.65 59.62 L 58.11 59.62 50.38 38.01 42.65 59.62 Z",
                               "M50,0a50,50,0,1,0,50,50A50,50,0,0,0,50,0ZM64.88,78.55l-3.24-9H39.12l-3.24,9H25.39L47,22h6.76L75.38,78.55Z");

  }

  private Node getSequenceAB() {
    return createCombinedShape("M 42.65 59.62 L 58.11 59.62 50.38 38.01 42.65 59.62 Z",
                               "M50,0a50,50,0,1,0,50,50A50,50,0,0,0,50,0ZM64.88,78.55l-3.24-9H39.12l-3.24,9H25.39L47,22h6.76L75.38,78.55Z",
                               "M174.9,44.54q6.91,0,6.92-6.33,0-6.1-8-6.11h-11V44.54Z",
                               "M174.9,54.57H162.81V68.49H173q5.25,0,7.42-1.87a6.18,6.18,0,0,0,2.18-4.94v-.11a7.14,7.14,0,0,0-1.79-5.09Q179,54.58,174.9,54.57Z",
                               "M170,0a50,50,0,1,0,50,50A50,50,0,0,0,170,0Zm23.29,63.16a14.36,14.36,0,0,1-4.69,11.16q-4.68,4.23-12.69,4.23H152.2V22h23.05q8.79,0,13,4.15t4.26,11.16a12.67,12.67,0,0,1-2.65,7.7q-2.64,3.57-8.43,4.55a12.88,12.88,0,0,1,8.73,4.64,13.72,13.72,0,0,1,3.09,8.8Z");
  }

  private Node getSequenceB() {
    return createCombinedShape("M54.9,44.54q6.91,0,6.92-6.33,0-6.1-8-6.11h-11V44.54Z",
                               "M54.9,54.57H42.81V68.49H53q5.25,0,7.42-1.87a6.18,6.18,0,0,0,2.18-4.94v-.11a7.14,7.14,0,0,0-1.79-5.09Q59,54.58,54.9,54.57Z",
                               "M50,0a50,50,0,1,0,50,50A50,50,0,0,0,50,0ZM73.29,63.16A14.36,14.36,0,0,1,68.6,74.32q-4.68,4.23-12.69,4.23H32.2V22h23q8.79,0,13,4.15t4.26,11.16a12.67,12.67,0,0,1-2.65,7.7q-2.64,3.57-8.43,4.55a12.88,12.88,0,0,1,8.73,4.64A13.72,13.72,0,0,1,73.29,63Z");
  }

  private Node getSequenceCD() {
    return createCombinedShape("M50,0a50,50,0,1,0,50,50A50,50,0,0,0,50,0ZM44.3,65.77a8.89,8.89,0,0,0,7,2.72,8.69,8.69,0,0,0,5.91-2.1,11,11,0,0,0,3.3-6h11a27.41,27.41,0,0,1-3.83,10.15,18.48,18.48,0,0,1-6.9,6.39,20,20,0,0,1-9.52,2.2A22.91,22.91,0,0,1,40.2,76.67a16.56,16.56,0,0,1-7-7.17,24.81,24.81,0,0,1-2.41-11.43V42.56a24.81,24.81,0,0,1,2.41-11.41,16.6,16.6,0,0,1,7-7.21,22.8,22.8,0,0,1,11.06-2.49,19.52,19.52,0,0,1,9.54,2.3,18.9,18.9,0,0,1,6.92,6.66A28.77,28.77,0,0,1,71.51,41h-11a14.83,14.83,0,0,0-1.94-4.8,9.19,9.19,0,0,0-3.13-3,8.22,8.22,0,0,0-4.14-1,8.86,8.86,0,0,0-7,2.74q-2.44,2.74-2.45,7.72V58.07C41.85,61.39,42.67,64,44.3,65.77Z",
                               "M180.67,34.82q-2.94-2.52-8.26-2.52h-8.28v36h8.28q5.33,0,8.26-2.52a8.93,8.93,0,0,0,2.94-7.16V42A8.93,8.93,0,0,0,180.67,34.82Z",
                               "M170,0a50,50,0,1,0,50,50A50,50,0,0,0,170,0Zm24.45,58.3q0,9.65-5.83,15t-16.48,5.3H153.48V22h18.7q10.65,0,16.46,5.3t5.81,15Z");
  }

  private Node createCombinedShape(String... pathDescriptors) {
    final Group group = new Group();
    for (String ps : pathDescriptors) {
      final SVGPath path = new SVGPath();
      path.setFillRule(FillRule.NON_ZERO);
      path.setContent(ps);
      group.getChildren().add(path);
    }
    return group;
  }

  public ObjectProperty<ScreenState.Sequence> sequenceProperty() {
    return sequence;
  }

  public ScreenState.Sequence getSequence() {
    return sequence.get();
  }

  public void setSequence(final ScreenState.Sequence value) {
    sequence.set(value);
  }


}
