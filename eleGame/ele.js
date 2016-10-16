var ele = {
	contextBox: document.all.contextBox,
	width: parseInt((document.body.clientWidth - 40) / 30),
	height: parseInt((document.body.clientHeight - 40) / 30),
	cache: [],
	score: 0, 
	highScore: 0,
	randomIndex: function(){
		var randomNumber = Math.random().toString();
		var index = parseInt(randomNumber.replace('0.', '')) % 4;
		return index + 1;
	},
	addScore: function(){
		ele.score++;
		if(ele.highScore < ele.score){
			ele.highScore = ele.score;
		}
		document.all.scoreBox.innerHTML = 'score: ' + ele.score + ', highScore: ' + ele.highScore;
	},
	addCache: function(value){
		ele.cache[value] = {value: value, time: 300};
	},
	setTagClass: function(eleTag, classIndex){
		eleTag.className = 'ele_' + classIndex;
		if(2 > classIndex){
			eleTag.setAttribute('left', true);
			eleTag.setAttribute('down', true);
			eleTag.setAttribute('right', '');
			eleTag.setAttribute('up', '');
		}else if(3 > classIndex){
			eleTag.setAttribute('left', true);
			eleTag.setAttribute('up', true);
			eleTag.setAttribute('right', '');
			eleTag.setAttribute('down', '');
		}else if(4 > classIndex){
			eleTag.setAttribute('right', true);
			eleTag.setAttribute('up', true);
			eleTag.setAttribute('left', '');
			eleTag.setAttribute('down', '');
		}else{
			eleTag.setAttribute('right', true);
			eleTag.setAttribute('down', true);
			eleTag.setAttribute('left', '');
			eleTag.setAttribute('up', '');
		}
	},
	randomEle: function(line, column){
		var index = ele.randomIndex();
		var eleTag = document.createElement("div");
		eleTag.id = line + '_' + column;
		ele.setTagClass(eleTag, index);
		eleTag.onclick = ele.eleClick;
		return eleTag;
	},
	randomLineEle: function(line){
		var eleLineTag = document.createElement("div");
		eleLineTag.id = line;
		for(var i = ele.width; i > 1; i--){
			eleLineTag.appendChild(ele.randomEle(line, i));
		}
		contextBox.appendChild(eleLineTag);
	},
	fillContextBox: function(){
		for(var j = ele.height; j > 1; j--){
			ele.randomLineEle(j);
		}
	},
	effectClick: function(eleId){
		var currentEle = document.getElementById(eleId);
		currentEle.setAttribute('clicking', true);
		var classArr = currentEle.className.split('_');
		var index = parseInt(classArr[1]) % 4 + 1;
		ele.setTagClass(currentEle, index);
		ele.waitEffectClick(currentEle);
		ele.addScore();
		currentEle.setAttribute('clicking', '');
	},
	reallyEffect: function(effectId, effectAttr){
		var effectEle = document.getElementById(effectId);
		if(!effectEle || effectEle.getAttribute('clicking')){
			return;
		}
		if(effectEle.getAttribute(effectAttr)){
			ele.addCache(effectId);
		}
	},
	waitEffectClick: function(eleTag){
		var idArr = eleTag.id.split('_');
		idArr[0] = parseInt(idArr[0]);
		idArr[1] = parseInt(idArr[1]);
		if(eleTag.getAttribute('right')){
			ele.reallyEffect(idArr[0] + '_' + (idArr[1] - 1), 'left');
		}
		if(eleTag.getAttribute('down')){
			ele.reallyEffect((idArr[0] - 1) + '_' + idArr[1], 'up');
		}
		if(eleTag.getAttribute('left')){
			ele.reallyEffect(idArr[0] + '_' + (idArr[1] + 1), 'right');
		}
		if(eleTag.getAttribute('up')){
			ele.reallyEffect((idArr[0] + 1) + '_' + idArr[1], 'down');
		}
	},
	eleClick: function(event){
		var currentEle = event.currentTarget;
		if(ele.isEffecting){
			return;
		}
		ele.isEffecting = true;
		ele.score = 0;
		currentEle.setAttribute('clicking', true);
		var classArr = currentEle.className.split('_');
		var index = parseInt(classArr[1]) % 4 + 1;
		ele.setTagClass(currentEle, index);
		ele.waitEffectClick(currentEle);
		currentEle.setAttribute('clicking', '');
	},
	eleStart: function(){
		ele.fillContextBox();
		setInterval(function(){
			var effectings = 0;
			for(var key in ele.cache){
				if(ele.cache[key]){
					if(1 > ele.cache[key].time){
						ele.effectClick(ele.cache[key].value);
						ele.cache[key] = '';
					}else{
						ele.cache[key].time = 0;
					}
					effectings++;
				}
			}
			if(1 > effectings){
				ele.isEffecting = false;
			}
		}, 300);
	}
};

window.error = function(e){
	console.info(e);
};

ele.eleStart();
