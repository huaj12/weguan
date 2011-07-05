function changeImage(obj,type)
{
    if(type=='off')
    {
        obj.firstChild.src=WeGaunConstants.staticService+"/images/homepage/I_button_1.gif";
    }else
    {
        obj.firstChild.src=WeGaunConstants.staticService+"/images/homepage/I_button.gif"
    }
}

function changeTitle(type)
{
    switch(type){
       case 'story':
           $('title').innerHTML="日&nbsp;记";
           $('context').innerHTML="用简单的文字分享你生活中的点滴精彩，让你的思绪飞舞在随性的文字中。";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_text.gif";
       break;
       case 'pic':
           $('title').innerHTML="图&nbsp;片";
           $('context').innerHTML="记录那微妙的一瞬间，茶余饭后翻出来看看，或与同事好友一同分享那快乐的时光。";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_img.gif";
       break; 
       case 'link':
           $('title').innerHTML="链&nbsp;接";
           $('context').innerHTML="看到什么新奇有趣的网页想告诉你的朋友们吗？出门在外找不到自己常去的网站吗？这就是它的作用。";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_link.gif";
       break; 
       case 'refer':
           $('title').innerHTML="摘&nbsp;录";
           $('context').innerHTML="一段很感人的文字有让你想要占为己有，慢慢回味吗？把它摘录在你的微观中吧，让我们一起感动！";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_quote.gif";
       break;    
       case 'video':
           $('title').innerHTML="视&nbsp;频";
           $('context').innerHTML="Kuso，英超进球，卡通片，连续剧。。。你一定有很多喜欢的视频想要自己保存或和朋友们分享吧？";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_video.gif";
       break; 
       case 'music':
           $('title').innerHTML="音&nbsp;乐";
           $('context').innerHTML="跳动的音符不限于5线之上，让这些音符通过微观传到每个朋友的耳中吧！";
           $('image').src=WeGaunConstants.staticService+"/images/homepage/i_music.gif";
       break;                                
    }
    
}