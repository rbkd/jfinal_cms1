var asynLoadImg = {
    asynLoadImg : function(para)//id,src,callback,errorSrc,repeatOfNot)
{
 //console.log(para);
  var imgloader= new window.Image();
  //当图片成功加载到浏览器缓存
  imgloader.onload =function(evt)  
  {
   if(typeof(imgloader.readyState)=='undefined')
    {
   imgloader.readyState = 'undefined';
    }
  //在IE8以及以下版本中需要判断readyState而不是complete
  if ((imgloader.readyState=='complete'||imgloader.readyState=="loaded")||imgloader.complete)	
  { 
    //console.log('width='+imgloader.width+',height='+imageloader.height);//读取原始图片大小
    para.callback({'msg':'ok','src':para.src,'id':para.id,'errorSrc':para.errorSrc,'repeatOrNot':para.repeatOrNot});
  }else{
    imgloader.onreadystatechange(evt);
  }
};

imgloader.onerror = function(evt)
{
  para.callback({'msg':'error','id':para.id});
};
                
 imgloader.onreadystatechange = function(e)
 { 
 //此方法只有IE8以及以下版本会调用		
 };
 imgloader.src=para.src;
},
    
//----------------------------------------
 loadResult : function(data)
{
  data =	data ||{} ;
  if(typeof(data.msg)!='undefined')
  {
  if(data.msg=='ok')
  {
         //这里使用了id获取元素，有点死板，建议读者自行扩展为css 选择符
         document.getElementById(''+data.id).src=data.src;
        
      }else{
     //这里图片加载失败，我们可以显示其他图片，防止大红叉
     document.getElementById(''+data.id).src=data.errorSrc;
  }
  }
},
  //---------------------------------------  
    loadResultAsBg : function(data){
        data =	data ||{} ;
        if(typeof(data.msg)!='undefined')
  {
  if(data.msg=='ok')
  {
         //这里使用了id获取元素，有点死板，建议读者自行扩展为css 选择符
         //document.getElementById(''+data.id).style.background='url('+data.src+') '+data.repeatOrNot;
        $('#'+data.id).css('background','url('+data.src+') '+data.repeatOrNot);
         //console.log("bg="+'url('+data.src+') '+data.repeatOrNot);
        //console.log(data.repeatOrNot);
        
      }else{
     //这里图片加载失败，我们可以显示其他图片，防止大红叉
     document.getElementById(''+data.id).style.background='url('+data.errorSrc+') ' +data.repeatOrNot;
  }
  }
    },
    
    //-----------------------------------------------
    loadResultAsBgImg : function(data){
        data =	data ||{} ;
        if(typeof(data.msg)!='undefined')
  {
  if(data.msg=='ok')
  {
         //这里使用了id获取元素，有点死板，建议读者自行扩展为css 选择符
         //document.getElementById(''+data.id).style.background='url('+data.src+') ';
        $('#'+data.id).css("background",'url('+data.src+') '+data.repeatOrNot);
         //console.log("bg="+'url('+data.src+') '+data.repeatOrNot);
         //console.log(data.repeatOrNot);
        
      }else{
     //这里图片加载失败，我们可以显示其他图片，防止大红叉
     document.getElementById(''+data.id).style.background='url('+data.errorSrc+') ' +data.repeatOrNot;
  }
  }
    }
}