package org.neuroph.samples.convolution.mnist;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.error.MeanSquaredError;
import org.neuroph.nnet.ConvolutionalNetwork;
import org.neuroph.nnet.learning.BackPropagation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import org.neuroph.nnet.comp.Dimension2D;
import org.neuroph.nnet.learning.ConvolutionalBackpropagation;

/**
 * Konvolucioni parametri
 * <p/>
 * Globalna arhitektura: Konvolucioni i pooling lejeri - naizmenicno (samo konvolucioni ili naizmenicno konvolccioni pooling)
 * Za svaki lajer da ima svoj kernel (mogu svi konvolucioni da imaju isti kernel, ili svi pooling isti kernel)
 * da mogu da zadaju neuron properties (transfer funkciju) za konvolucioni i pooling lejer(input)
 * Konektovanje lejera? - po defaultu full connect (ostaviti api za custom konekcije)
 * <p/>
 * addFeatureMaps...
 * connectFeatureMaps
 * <p/>
 * Helper utility klasa...
 * <p/>
 * Osnovni kriterijumi:
 * 1. Jednostavno kreiranje default neuronske mreze
 * 2. Laka customizacija i kreiranje custom arhitektura: konvolucionih i pooling lejera i transfer/input funkcija
 * Napraviti prvo API i ond aprilagodti kod
 * <p/>
 * ------------------------
 * <p/>
 * promeniti nacin kreiranja i dodavanja feature maps layera
 * resiti InputMaps Layer, overridovana metoda koja baca unsupported exception ukazuje da nesto nije u redu sa dizajnom
 * Da li imamo potrebe za klasom kernel  - to je isto kao i dimension?
 * <p/>
 * zasto je public abstract void connectMaps apstraktna? (u klasi FeatureMapsLayer)
 * <p/>
 * InputMapsLayer konstruktoru superklase prosledjuje null...
 * <p/>
 * fullConnectMapLayers
 *
 * 
 * Same as CNNMNIST just with hardoced params
 * 
 * @author zoran
 */


public class MNISTExample {

    private static Logger LOG = LoggerFactory.getLogger(MNISTExample.class);

    public static void main(String[] args) {
        try {

            DataSet trainSet = MNISTDataSet.createFromFile(MNISTDataSet.TRAIN_LABEL_NAME, MNISTDataSet.TRAIN_IMAGE_NAME, 60);
            DataSet testSet = MNISTDataSet.createFromFile(MNISTDataSet.TEST_LABEL_NAME, MNISTDataSet.TEST_IMAGE_NAME, 10);

            Dimension2D inputDimension = new Dimension2D(32, 32);
            Dimension2D convolutionKernelDim = new Dimension2D(5, 5);
            Dimension2D poolingKernelDim = new Dimension2D(2, 2);

            ConvolutionalNetwork convolutionNetwork = new ConvolutionalNetwork.Builder(inputDimension, 1)
                    .withConvolutionLayer(convolutionKernelDim, 10)
                    .withPoolingLayer(poolingKernelDim)
//                    .withConvolutionLayer(convolutionKernelDim, 3)
//                    .withPoolingLayer(poolingKernelDim)
//                    .withConvolutionLayer(convolutionKernel, 3)
                    .withFullConnectedLayer(100)
                    .withFullConnectedLayer(10)
                    .build();

            ConvolutionalBackpropagation backPropagation = new ConvolutionalBackpropagation();
            backPropagation.setLearningRate(0.0001);
            backPropagation.setMaxError(0.01);
            //backPropagation.setMaxIterations(1000);
            backPropagation.addListener(new LearningListener());
            backPropagation.setErrorFunction(new MeanSquaredError());

            convolutionNetwork.setLearningRule(backPropagation);
            backPropagation.addListener(new LearningListener());

            System.out.println("Started training...");
            
            convolutionNetwork.learn(trainSet);
                        
            System.out.println("Done training!");
      
//            CrossValidation crossValidation = new CrossValidation(convolutionNetwork, testSet, 6);
//            crossValidation.run();
            
//           ClassificationMetrics validationResult = crossValidation.computeErrorEstimate(convolutionNetwork, trainSet);
           // Evaluation.runFullEvaluation(convolutionNetwork, testSet);
            
            convolutionNetwork.save("mnist.nnet");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static class LearningListener implements LearningEventListener {


        long start = System.currentTimeMillis();

        @Override
        public void handleLearningEvent(LearningEvent event) {
            BackPropagation bp = (BackPropagation) event.getSource();
            LOG.info("Current iteration: " + bp.getCurrentIteration());
            LOG.info("Error: " + bp.getTotalNetworkError());
            LOG.info("Calculation time: " + (System.currentTimeMillis() - start) / 1000.0);
         //   neuralNetwork.save(bp.getCurrentIteration() + "CNN_MNIST" + bp.getCurrentIteration() + ".nnet");
            start = System.currentTimeMillis();
//            NeuralNetworkEvaluationService.completeEvaluation(neuralNetwork, testSet);
        }

    }    


}
