package com.example.myloginapp;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.BOWImgDescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class SearchController {

    public static int compareFeature(Bitmap img, Bitmap compareImg) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        int retVal=0;

        if(compareImg==null || img==null)
            return 3;

        Mat src = new Mat();
        Utils.bitmapToMat(img, src);

        Mat compare = new Mat();
        Utils.bitmapToMat(compareImg,compare);

        Mat hsvSrc = new Mat();
        Mat hsvCom = new Mat();

        Imgproc.cvtColor(src,hsvSrc,Imgproc.COLOR_BGR2HSV);
        Imgproc.cvtColor(compare,hsvCom,Imgproc.COLOR_BGR2HSV);

        List<Mat> listImg1 = new ArrayList<Mat>();
        List<Mat> listImg2 = new ArrayList<Mat>();

        listImg1.add(hsvSrc);
        listImg2.add(hsvCom);

        MatOfFloat ranges = new MatOfFloat(0,255);
        MatOfInt histSize = new MatOfInt(50);
        MatOfInt channels = new MatOfInt(0);

        Mat histImg1=new Mat();
        Mat histImg2=new Mat();

        Imgproc.calcHist(listImg1,channels,new Mat(),histImg1,histSize,ranges);
        Imgproc.calcHist(listImg2,channels,new Mat(),histImg2,histSize,ranges);

        Core.normalize(histImg1,histImg1,0,1,Core.NORM_MINMAX,-1,new Mat());
        Core.normalize(histImg2,histImg2,0,1,Core.NORM_MINMAX,-1,new Mat());

        double result0,result1,result2,result3;

        result0=Imgproc.compareHist(histImg1,histImg2,0);
        result1=Imgproc.compareHist(histImg1,histImg2,1);
        result2=Imgproc.compareHist(histImg1,histImg2,2);
        result3=Imgproc.compareHist(histImg1,histImg2,3);

        int count=0;
        if(result0>0.9) count++;
        if(result1<0.1) count++;
        if(result2>1.5) count++;
        if(result3<0.2) count++;

        if(count>=2) retVal=1;
        return retVal;
    }
}
