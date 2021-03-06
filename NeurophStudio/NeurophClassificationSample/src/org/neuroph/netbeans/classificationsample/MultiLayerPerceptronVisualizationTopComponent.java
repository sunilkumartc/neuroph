package org.neuroph.netbeans.classificationsample;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;
import org.netbeans.api.settings.ConvertAsProperties;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.data.DataSet;
import org.neuroph.netbeans.visual.TrainingController;
import org.neuroph.netbeans.visual.NeuralNetAndDataSet;
import org.neuroph.netbeans.project.NeurophProjectFilesFactory;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Top component which displays visualization of data set na multi layer perceptron classification.
 */
@ConvertAsProperties(dtd = "-//org.neuroph.netbeans.classificationsample.mlperceptron//MultiLayerPerceptronVisualizationTopComponent//EN",
        autostore = false)
public final class MultiLayerPerceptronVisualizationTopComponent extends TopComponent implements LearningEventListener {

    //private static MultiLayerPerceptronVisualizationTopComponent instance;
    private static final String PREFERRED_ID = "MultiLayerPerceptronVisualizationTopComponent";
 //   private Visualization2DPanel visualizationPanel;
    private MultiLayerPerceptronClassificationSamplePanel controllsPanel;
    private MlpClassificationSampleControlsTopComponent stc;
    private DataSet trainingSet;
    private int tsCount = 0;
    private NeuralNetwork neuralNetwork;
    private NeuralNetAndDataSet neuralNetAndDataSet;
    private TrainingController trainingController;
    private Thread firstCalculation = null;
    private int learningIterationCounter = 0;
    private InstanceContent content;
    private AbstractLookup aLookup;
    private DropTargetListener dtListener;
    private DropTarget dropTarget;
    private int acceptableActions = DnDConstants.ACTION_COPY;
    private boolean trainSignal = false;
    private ArrayList<Double> setValues;
    private ArrayList<Double[]> neuralNetworkInputs;
    
    private static int topComponentCount=0;
    
    public MultiLayerPerceptronVisualizationTopComponent() {
        initComponents();
        topComponentCount++;
        setName(NbBundle.getMessage(MultiLayerPerceptronVisualizationTopComponent.class, "CTL_MultiLayerPerceptronSampleTopComponent") + " " +topComponentCount);
        setToolTipText(NbBundle.getMessage(MultiLayerPerceptronVisualizationTopComponent.class, "HINT_MultiLayerPerceptronSampleTopComponent"));
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);
        
        content = new InstanceContent();
        aLookup = new AbstractLookup(content);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        visualizationPanel = new org.neuroph.netbeans.classificationsample.Visualization2DPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(clearButton, org.openide.util.NbBundle.getMessage(MultiLayerPerceptronVisualizationTopComponent.class, "MultiLayerPerceptronVisualizationTopComponent.clearButton.text")); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 3, 4, 713);
        jPanel1.add(clearButton, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout visualizationPanelLayout = new javax.swing.GroupLayout(visualizationPanel);
        visualizationPanel.setLayout(visualizationPanelLayout);
        visualizationPanelLayout.setHorizontalGroup(
            visualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        visualizationPanelLayout.setVerticalGroup(
            visualizationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );

        add(visualizationPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel jPanel1;
    private org.neuroph.netbeans.classificationsample.Visualization2DPanel visualizationPanel;
    // End of variables declaration//GEN-END:variables


    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public Lookup getLookup() {
        return new ProxyLookup(new Lookup[]{
            super.getLookup(),
            aLookup
        });
    }

    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        readPropertiesImpl(p);
        return this;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public DataSet getTrainingSet() {
        return trainingSet;
    }

    public void setTrainingSet(DataSet trainingSet) {
        this.trainingSet = trainingSet;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public boolean isTrainSignal() {
        return trainSignal;
    }

    public Visualization2DPanel getVisualizationPanel() {
        return visualizationPanel;
    }

    public void setVisualizationPanel(Visualization2DPanel inputSpacePanel) {
        this.visualizationPanel = inputSpacePanel;
    }

    public void setTrainSignal(boolean trainSignal) {
        this.trainSignal = trainSignal;
    }

    public ArrayList<Double[]> getInputs() {
        return neuralNetworkInputs;
    }

    public void setInputs(ArrayList<Double[]> inputs) {
        this.neuralNetworkInputs = inputs;
    }
    private int[] storedInputs; // 2 inputs chosen for visualization

    public void setStoredInputs(int[] storedInputs) {
        this.storedInputs = storedInputs;
    }

    public boolean isAllPointsRemoved() {
        return visualizationPanel.isAllPointsRemoved();
    }

    public boolean isPointDrawed() {
        return visualizationPanel.isPointDrawed();
    }

    public boolean isDrawingLocked() {
        return visualizationPanel.isDrawingLocked();
    }

    public void setDrawingLocked(boolean flag) {
        visualizationPanel.setDrawingLocked(flag);
    }

    public void visualizationPreprocessing() {
        controllsPanel.visualizationPreprocessing();
    }

    public void setVisualizationStarted(boolean flag) {
        visualizationPanel.setVisualizationStarted(flag);
    }

    /*
     * If point is drawed on panel, this method registers that event
     */
    public void setPointDrawed(boolean drawed) {
        visualizationPanel.setPointDrawed(drawed);
    }

    public ArrayList<Double> getSetValues() {
        return setValues;
    }

    public void setSetValues(ArrayList<Double> setValues) {
        this.setValues = setValues;
    }

    /*
     * Draws points from dataset, with 2 specified inputs
     */
    public void drawPointsFromTrainingSet(DataSet dataSet, int[] inputs) {
 //       try {
            visualizationPanel.setAllPointsRemoved(false);
            visualizationPanel.drawPointsFromDataSet(dataSet, inputs);
//        } catch (Exception e) {
//        }
    }

    /*
     * Initializes panel regarding coordinate system domain (positive, or positive and negative inputs).
     * 
     */
//    public void initializePanel(boolean positiveCoordinates) {
//        if (visualizationPanel != null) {
//            this.remove(visualizationPanel);
//        }
//        visualizationPanel = new Visualization2DPanel();
//        visualizationPanel.setPositiveInputsOnly(positiveCoordinates);
//
//        add(visualizationPanel, BorderLayout.CENTER);
//
//        repaint();
//    }

    /**
     * Creates new form BackpropagationSample
     * used in ViewManager probably not needed anymore
     */
    public void setTrainingSetForMultiLayerPerceptronSample(ObservableTrainingSet ps) {
        trainingSet = new DataSet(2, 1);
        
        stc = MlpClassificationSampleControlsTopComponent.findInstance();
        controllsPanel = stc.getSampleControlsPanel();
        controllsPanel.setMlpSampleTc(this);
        stc.open();

   //     initializePanel(false);
      
        
        this.dtListener = new DTListener();
        this.dropTarget = new DropTarget(
                this,
                this.acceptableActions,
                this.dtListener,
                true);
    }

    /*
     * Creates neural network file within selected project
     */
    public void createNeuralNetworkFile(NeuralNetwork neuralNetwork) {
        NeurophProjectFilesFactory.getDefault().createNeuralNetworkFile(neuralNetwork);
    }

    /*
     * If custom (manual) dataset is specified, training set and training set name is created
     */
    public void customDataSetCheck() {
        if (visualizationPanel.isPointDrawed()) {
            trainingSet = visualizationPanel.getTrainingSet();
            tsCount++;
            trainingSet.setLabel("MlpSampleTrainingSet" + tsCount);
        }
    }

    /*
     * If custom (manual) dataset is specified, training set file is created
     */
    public void sampleTrainingSetFileCheck() {
        if (visualizationPanel.isPointDrawed()) {
            NeurophProjectFilesFactory.getDefault().createTrainingSetFile(trainingSet);
            visualizationPanel.setPointDrawed(false);
        }
    }

    /*
     * During training mode, if Show Points check box is selected, redrawing of dataset is required
     */
    public void showPointsOptionCheck() {
        if (MultiLayerPerceptronClassificationSamplePanel.SHOW_POINTS && visualizationPanel.isAllPointsRemoved()) {
            try {
                visualizationPanel.setAllPointsRemoved(false);
                drawPointsFromTrainingSet(trainingSet, InputSettingsDialog.getInstance().getStoredInputs());
            } catch (Exception e) {
            }
        }
    }

    /*
     * Collects all the information needed for training neural network
     */
    public void trainingPreprocessing() {
        trainSignal = true;
        neuralNetAndDataSet = new NeuralNetAndDataSet(neuralNetwork, trainingSet);
        trainingController = new TrainingController(neuralNetAndDataSet);
        neuralNetwork.getLearningRule().addListener(this);//adds learning rule to observer
        trainingController.setLmsParams(controllsPanel.getLearningRate(), controllsPanel.getMaxError(), controllsPanel.getMaxIteration());
        LMS learningRule = (LMS) this.neuralNetAndDataSet.getNetwork().getLearningRule();
        if (learningRule instanceof MomentumBackpropagation) {
            ((MomentumBackpropagation) learningRule).setMomentum(controllsPanel.getMomentum());
        }
        getVisualizationPanel().setNeuronColors(neuralNetwork);
    }

    /*
     * Stops training
     */
    public void stop() {
        neuralNetAndDataSet.stopTraining();
    }

    /*
     * Clears all points from panel
     */
    public void clear() {
        visualizationPanel.clearPoints();
    }

    /*
     * Generates set values from [-k,k] domain, in order to simulate all neural network inputs.
     * This set is later used for generating variations with repetition of class k=numberOfinputs.
     * For each variation (in our case simulated input) we choose exactly 2 inputs for 2D visualization
     */
    public ArrayList<Double> generateSetValues(int size, double coef) {
        setValues = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double value = 1 - i * coef;
            setValues.add(value);
        }
        return setValues;
    }

    /*
     * Removes neural network and dataset from content
     */
    public void removeNetworkAndDataSetFromContent() {
        try {
            content.remove(neuralNetAndDataSet);
            content.remove(trainingController);
        } catch (Exception ex) {
        }
        MultiLayerPerceptronVisualizationTopComponent.this.requestActive();
    }

    /*
     * Regarding selected dataset, this method initializes different coordinate system
     */
    public void coordinateSystemDomainCheck() {
        boolean positive = true;//only positive inputs are detected
        loop:
        for (int i = 0; i < trainingSet.size(); i++) {
            double[] inputs = trainingSet.getRowAt(i).getInput();
            for (int j = 0; j < inputs.length; j++) {
                if (inputs[j] < 0) {
                    positive = false;//both positive and negative inputs are detected
                    break loop;
                }
            }
        }
  //      initializePanel(positive);//panel initialization
        stc.getSampleControlsPanel().setCheckPoints(positive);//updating Swing components 
    }

    /*
     * This method shows the information, eg. current name of dataset and neural network that are used for training
     */
    public void updateNeuralNetAndDataSetInfo(NeuralNetwork neuralNetvork, DataSet dataSet) {

        MultiLayerPerceptronClassificationSamplePanel mlp = stc.getSampleControlsPanel();// gets sampleControlsPanel
        if (dataSet != null) {
            if (dataSet.getLabel() != null) {
                mlp.setDataSetInformation(dataSet.getLabel());//if dataSet and dataSet label are not null, sets dataSetInformation to that label
            } else {
                mlp.setDataSetInformation("Not selected");//else if just the dataSet label is not null, it sets the information to "Not selected"
            }
        } else {
            mlp.setDataSetInformation("Not selected");//else it sets the information to "Not selected"
        }
        if (neuralNetvork != null) {
            if (neuralNetvork.getLabel() != null) {
                mlp.setNeuralNetworkInformation(neuralNetvork.getLabel());//if nauralNetwork and neuralNetwork label are not null, sets neuralNetworkInformation to that label
            } else {
                mlp.setNeuralNetworkInformation("Not selected");//else if just the neuralNetwork label is not null, it sets the information to "Not selected"
            }
        } else {
            mlp.setNeuralNetworkInformation("Not selected");//else it sets the information to "Not selected"
        }
    }

    /*
     * During neural network training process, at  particular moment, the same process pauses for a moment,
     * and neural network is passed as an function argument.
     * This method simulates all the network inputs in selected domain: [-1,1] or [0,1].
     * These inputs serve as inputs to trained network.
     * Once the output is calculated, through each iteration, panel is updated with all 
     * current outputs, and specific training result is shown on panel.
     * This process repeats itself once the training is completed.
     */
    public void visualizeNeuralNetworkAnswer(NeuralNetwork nn) {
        if (nn != null) {
            visualizationPanel.setNeuralNetwork(nn);
            double initialCoordinate;
         //   double coefficient = 0.0357142857142857;//1/57=0.0357142857142857 // zapravo 2/size-1
            double coefficient = 0.025316456;
            int size = 79; // 56
            /*
             * Sets parameters either only for positive inputs or positive and negative inputs
             */
            if (visualizationPanel.positiveInputsOnly()) {
                initialCoordinate = 0.0;
            } else {
                initialCoordinate = 1.0;
            }
            
            for (int i = 0; i < neuralNetworkInputs.size(); i++) {
                /*
                 * TODO: Remove this unboxing
                 * Cannot use Double type as neural network input,
                 * so conversion from Double to double type is required
                 */
                Double[] incompatibleInput = neuralNetworkInputs.get(i);
                double[] input = new double[incompatibleInput.length];
                for (int j = 0; j < input.length; j++) {
                    input[j] = incompatibleInput[j];
                }
                
                nn.setInput(input);
                nn.calculate();
                double output = nn.getLayerAt(nn.getLayersCount() - 1).getNeuronAt(0).getOutput();
                double xInput = input[storedInputs[0]] + initialCoordinate;
                double yInput = input[storedInputs[1]] + initialCoordinate;
               
                // ovo iscrtati jednom u offscreen slici i onda posle samo iskoristiti, ne ponavljati iscrtavanje
                int x; 
                int y;
                
                /*
                 * Transformation from Descartes' coordintes to panel coordinates
                 */
                if (visualizationPanel.positiveInputsOnly()) {//output for positive input only
                    x = (int) Math.abs(xInput * size);
                    y = size - (int) Math.abs(yInput * size);
                } else {//output for both positive and negative input
                    x = (int) Math.abs((xInput) / coefficient);
                    y = size - (int) Math.abs((yInput) / coefficient);
                }
                
                visualizationPanel.setGridPoints(x, y, output);//sets the grid points according to the output                
            }
            visualizationPanel.repaint();//repaints visualizationPanel commponent
        }
    }

    /**
     * Actual drawing is trigered from here after each 10th learning  teration
     */
    @Override
    public void handleLearningEvent(LearningEvent le) {
        learningIterationCounter++;//iteration counter
        
        if (learningIterationCounter % 10 == 0) { // redraw after 10 learning iterations
//            if (neuralNetworkInputs == null) reGenerateNeuralNetworkInputs(neuralNetAndDataSet.getDataSet());
            NeuralNetwork nnet = neuralNetAndDataSet.getNetwork();
            nnet.pauseLearning();//pause
            visualizeNeuralNetworkAnswer(nnet);//calculating network response and draw it
            nnet.resumeLearning();//resume
        }
    }

    class DTListener implements DropTargetListener {

        //accepts the drag using the operation, returned by the getDropAction method, as the parameter
        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            dtde.acceptDrag(dtde.getDropAction());
        }

        @Override
        public void dragExit(DropTargetEvent dte) {
        }

        //accepts the drag using the operation, returned by the getDropAction method, as the parameter
        @Override
        public void dragOver(DropTargetDragEvent dtde) {
            dtde.acceptDrag(dtde.getDropAction());
        }

        //accepts the drag using the operation, returned by the getDropAction method, as the parameter
        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {
            dtde.acceptDrag(dtde.getDropAction());
        }

        @Override
        public void drop(DropTargetDropEvent e) {
            Transferable transferable = e.getTransferable();//returns the Transferable object associated with the drop
            Node node = NodeTransfer.node(transferable, NodeTransfer.DND_COPY_OR_MOVE);

            DataSet dataSet = node.getLookup().lookup(DataSet.class);               // get the DataSet objects from node lookup
            NeuralNetwork neuralNet = node.getLookup().lookup(NeuralNetwork.class); // get the NeuralNetwrok object from node lookup

            if (dataSet != null) {//if dataSet is not null
                clear();//clear the points on the visualizationPanel
                setPointDrawed(false);//setPointDrawed to false
                getVisualizationPanel().setDrawingLocked(true);//locks the drawing on the visualizationPanel
                trainingSet = dataSet;//assings the value of dataSet to trainingSet
                InputSettingsDialog isd = InputSettingsDialog.getInstance();//returns the instance of InputSettingsDialog(Singleton patern)
                isd.initDataSetInfo(trainingSet);//sets the dataSetInfo in combo boxes, first and second input(InputSettings Dialog class)
                isd.setVisible(true);//sets input isd(InputSettingsDialog) to be visible
                updateNeuralNetAndDataSetInfo(getNeuralNetwork(), trainingSet);//updates neuralNet and dataSet information
                coordinateSystemDomainCheck();//checks input(only positive or both positive and negative)
                getVisualizationPanel().drawPointsFromDataSet(trainingSet, isd.getStoredInputs());//draws points from trainingSet
            }

            if (neuralNet != null) {//if neuralNet is not null
                setNeuralNetwork(neuralNet);//sets the neuralNetwork
                updateNeuralNetAndDataSetInfo(getNeuralNetwork(), trainingSet);//updates neuralNet and dataSet information
            }

            if ((trainingSet != null) && (getNeuralNetwork() != null)) {//if both trainingSet and neuralNet are not null
                removeNetworkAndDataSetFromContent();//removes neuralNetAndDataSet from content
                trainingPreprocessing();//Collects all the information needed for training neural network
                content.add(neuralNetAndDataSet);//adds neuralNetAndDataSet to content
                content.add(trainingController);//adds trainingController to content
                updateNeuralNetAndDataSetInfo(getNeuralNetwork(), trainingSet);////updates neuralNet and dataSet information
                requestActive();
            }

            e.dropComplete(true);//notifies the DragSource that the drop transfer(s) are completed
        }
    }
}
