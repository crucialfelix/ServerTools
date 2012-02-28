

SimpleLabel : SCViewHolder {

	*new { arg layout,string,bounds,font;
		var new;
		new = this.prNew(layout,string,bounds,font);
		new.background_(Color(0.9843, 0.9843, 0.9843, 1.0))
			.align_(\left);
		^new
	}
	*prNew { arg layout,string,bounds,font;
		var width,height;
		string = string.asString;
		if(font.isNil,{ font =  GUI.font.new(*GUI.skin.fontSpecs) });
		# width, height = bounds.asArray;
		width = width ?? {(string.bounds(font).width + 6)};
		height = height ?? {GUI.skin.buttonHeight};
		^super.new.makeViewWithStringSize(layout,width,height)
			.font_(font)
			.label_(string)
	}

	makeViewWithStringSize { arg layout,optimalWidth,minHeight;
		var x,y, rect;
		x = (optimalWidth + 10).max(20);
		y = minHeight ?? {GUI.skin.buttonHeight};
		if((layout.isNil or: { layout.isKindOf(PageLayout) }),{ layout = layout.asFlowView; });
		this.view = this.class.viewClass.new(layout,Rect(0,0,x,y));
		this.view.keyDownAction = {nil};
	}
	*viewClass { ^GUI.staticText }

	label_ { arg string;
		view.string_(" " ++ string ++ " ");
	}
	bold { arg fontSize=11;
		this.font = GUI.font.new("Helvetica-Bold",fontSize);
	}
	color_ { arg color;
		view.stringColor = color
	}
}


SimpleButton : SimpleLabel {

	var <action;

	*new { arg layout,title,action;
		^super.prNew(layout,title).action_(action)
	}
	action_ { arg argAction;
		action = argAction;
		view.action = action;
	}
	*viewClass { ^GUI.button }
	label_ { arg title;
		var skin = GUI.skin;
		view.states_([[title,skin.fontColor,skin.background]]);
	}
	color_ { arg color;
		var s;
		s = view.states;
		s.at(0).put(1,color);
		view.states = s;
	}
	background_ { arg color;
		var s;
		s = view.states;
		s.at(0).put(2,color);
		view.states = s;
	}
}


