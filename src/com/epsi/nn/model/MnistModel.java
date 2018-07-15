package com.epsi.nn.model;

import com.epsi.nn.mnist.MnistImageFile;
import com.epsi.nn.mnist.MnistLabelFile;

public class MnistModel {
    MnistImageFile imageFile;
    MnistLabelFile labelFile;

    public MnistModel(MnistImageFile imageFile, MnistLabelFile labelFile) {
        this.imageFile = imageFile;
        this.labelFile = labelFile;
    }

    public MnistImageFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MnistImageFile imageFile) {
        this.imageFile = imageFile;
    }

    public MnistLabelFile getLabelFile() {
        return labelFile;
    }

    public void setLabelFile(MnistLabelFile labelFile) {
        this.labelFile = labelFile;
    }
}
