package cn.zjt.iot.oncar.server.svm;

import java.io.IOException;

public class TestSVM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] trainArg = { "C:\\Users\\Jingtang Zhang\\Desktop\\train",	// 训练集
				"C:\\Users\\Jingtang Zhang\\Desktop\\model.txt" };			// 模型输出

		String[] predictArg = { "C:\\Users\\Jingtang Zhang\\Desktop\\test.txt", // 测试集
				"C:\\Users\\Jingtang Zhang\\Desktop\\model.txt",			// 模型输入
				"C:\\Users\\Jingtang Zhang\\Desktop\\result.txt" };			// 结果输出

		System.out.println("训练开始");
		long start = System.currentTimeMillis();
		try {
			svm_train.main(trainArg);	// 训练
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Training time:" + (System.currentTimeMillis() - start));

		try {
			svm_predict.main(predictArg);	// 预测
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
