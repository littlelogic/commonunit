package com.badlogic.Jetpack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModelWithLiveData extends ViewModel {


   private MutableLiveData<Integer> linkNumber;

   public MutableLiveData<Integer> getLinkNumber(){
      if (linkNumber == null) {
         linkNumber = new MutableLiveData<Integer>();
         linkNumber.setValue(0);
      }
      return linkNumber;
   }

   public void addLinkNumber(int  n){
      linkNumber.setValue(linkNumber.getValue() + n);
   }


}
