import java.util.Random;


public class PSOEngine {
	int modelCount=300;
	
	TrainingData[] trainingSet=new TrainingData[3000];
	NNModel[] nnmodel=new NNModel[modelCount];
	
	
	int finalModel=-1;
	
	
	public void setTrainingSet(TrainingData[] trainingSet){
		this.trainingSet=trainingSet;
	}
	
	
	public void generateRandomNNSet(){
		for(int i=0;i<300;i++){
			nnmodel[i]=new NNModel();
			nnmodel[i].randomParameter();
			nnmodel[i].setData(trainingSet);
		}
	}
	
	public void startPSOAlgo(){
		//temp_nnmodel=new NNModel[300];
		double globalMinError=10000000;
		
		for(int i=1;i<=300;i++){
			double localMinError=100000000;
			int localModel=-1;
			
			
			System.out.printf("第%d次訓練\n",i);
			
			
			
			for(int j=0;j<300;j++){
				if(nnmodel[j].countTotalError()<localMinError){
					localMinError=nnmodel[j].countTotalError();
					localModel=j;
					
					System.out.printf("發現區域最小值%f\n",nnmodel[localModel].countErrorRadious());
					
				}
				
			}
			
			if(finalModel!=-1){
				this.adjustParticle(localModel);
			}
			
			
			
			
			if(localMinError<globalMinError){
				globalMinError=localMinError;
				finalModel=localModel;
				
				System.out.printf("發現最小值%f\n",nnmodel[finalModel].countErrorRadious());
			}
			
			
			
		}
		
		
		
		System.out.printf("final total error:%f\n",globalMinError);
		
		
		
		
	}
	
	private void adjustParticle(int localModel) {
		double[] localParameter=new double[nnmodel.length];
		double[] finalParameter=new double[nnmodel.length];
		for(int j=0;j<36;j++){
			localParameter[j]=nnmodel[localModel].getParameter(j);
			finalParameter[j]=nnmodel[finalModel].getParameter(j);
		}
		
		
		
		// TODO Auto-generated method stub
		for(int i=0;i<nnmodel.length;i++){
			for(int j=0;j<36;j++){
				//System.out.printf("%d             %d\n",localModel,finalModel);
				double v=nnmodel[i].getVelocity(j)+0.5*(localParameter[j]-nnmodel[i].getParameter(j))+0.5*(finalParameter[j]-nnmodel[i].getParameter(j));
				nnmodel[i].setVelocity(j,v);
				nnmodel[i].setParameter(j,nnmodel[i].getParameter(j)+v);
			}
			
		}
		
		
		
	}


	public NNModel getFinalModel(){
		return nnmodel[finalModel];
	}

	
	
}
