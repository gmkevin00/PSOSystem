import javax.swing.JFrame;




import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PSOFrame extends JFrame {
	JButton startButton=new JButton("Move Car");
	
	TrainingData[] trainingSet=new TrainingData[3000];
	
	NNModel finalModel;
	
	public PSOFrame(){
		super("PSO");
		GetTrainData getTrainData=new GetTrainData();
		getTrainData.getDataFromFile();
		trainingSet=getTrainData.getData();
		
		PSOEngine psoengine=new PSOEngine();
		psoengine.setTrainingSet(trainingSet);
		psoengine.generateRandomNNSet();
		psoengine.startPSOAlgo();
		finalModel=psoengine.getFinalModel();
		
		
		
		PSOPanel psoPanel=new PSOPanel();
		add(psoPanel,BorderLayout.CENTER);
		add(startButton,BorderLayout.SOUTH);
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				//genePanel.moveCar(20);
				
				//repaint();
				Thread moveThread=new Thread(){
					public void run(){
						for(int i=0;i<1000;i++){
							double[] inputData=new double[6];
							inputData[1]=psoPanel.getpointX()/10;
							inputData[2]=psoPanel.getpointY()/10;
							inputData[3]=psoPanel.countFrontLen()/10;
							inputData[4]=psoPanel.countRightLen()/10;
							inputData[5]=psoPanel.countLeftLen()/10;
							System.out.printf("%f     %f     %f     %f     %f     \n",inputData[1],inputData[2],inputData[3],inputData[4],inputData[5]);
							
							double radious=finalModel.countOutput(inputData);
							System.out.printf("Âà%f«×\n",radious);
							
							psoPanel.moveCar(radious);
							repaint();
							
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				moveThread.start();	
			}
		});
	}
	
}

