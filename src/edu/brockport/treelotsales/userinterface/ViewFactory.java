package edu.brockport.treelotsales.userinterface;

import edu.brockport.treelotsales.impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if (viewName.equals("TLCView")) {
			return new TLCView(model);
		}else if (viewName.equals("ScoutView")){
			return new ScoutView(model);
		}else if(viewName.equals("TreeView")){
			return new TreeView(model);
		}else if(viewName.equals("ScoutSearch")){
			return new SearchScoutsView(model);
		}else if(viewName.equals("ScoutCollectionView")){
			return new ScoutCollectionView(model);
		}else if(viewName.equals("UpdateScoutView")){
			return new UpdateScoutView(model);
		}else {
			return null;
		}
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
