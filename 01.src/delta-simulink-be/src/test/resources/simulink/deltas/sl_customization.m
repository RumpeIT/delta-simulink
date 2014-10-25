
function sl_customization(cm)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%TODO Add RWTH custom header
%load with
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

global ckMode;

%if(lineMap==[])

    ckMode='normal';
%end

%% Register custom menu function.
cm.addCustomMenuFcn('Simulink:PreContextMenu', @getMyMenuItems);
cm.addCustomMenuFcn('Simulink:ToolsMenu', @getDeltaSimMenue);
end

%% Define the custom menu functions.
function schemaFcns = getMyMenuItems(callbackInfo) 
  schemaFcns = {@addSimulinkBlock}; 
end

function schemaFcns = getDeltaSimMenue(callbackInfo) 
  schemaFcns = {@deltaMen,@deltaConf}; 
end
function schema = deltaMen(callbackInfo)     
  schema = sl_container_schema;
  schema.label  = 'DeltaSimulink Operations';
  mymods= cellstr({'delta','normal'});
  for i=1:2,
  mymod=char(mymods{i});
  childFunc(i)={{@deltaSimulinkMode,{mymod}}};
  end
  schema.childrenFcns = childFunc;
end
function schema = deltaConf(callbackInfo)     
  schema = sl_container_schema;
  schema.label  = 'DeltaSimulink Confiuration';
  mymods= cellstr({'Apply Delta to Model File','Apply Delta to Current Model'});
  mymod=char(mymods{1});
  childFunc(1)={{@deltaSimulinkMode,{mymod}}};
  schema.childrenFcns = childFunc;
end
function schema=deltaSimulinkMode(callbackInfo)
   global ckMode;
   schema = sl_action_schema;
   schema.label = callbackInfo.userdata{1};
   mymod=callbackInfo.userdata{1};
   schema.userdata = callbackInfo.userdata;
   
   if(strcmp(ckMode,mymod)==1)
   schema.state = 'Disabled';
   else
   schema.state = 'Enabled';    
   end
   schema.callback = @slDeltaMode;
end
   
function slDeltaMode(inArgs)
  
   global ckMode;
   
   mymodus     = inArgs.userdata{1};
   ckMode=mymodus;
   %TODO Define light Blue see: for tutorial
   mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
   blocks=find_system( 'FindAll','on', 'Type', 'Block');
   lines=find_system( 'FindAll','on', 'Type', 'Line');
   if(strcmp(mymodus,'delta')==1)
       
    for block=1:length(blocks),
    %uDat=get_param(blocks(block), 'UserData');
    
    if(isempty(get_param(blocks(block),'UserData')))
    uDat=BlockDat(get_param(blocks(block),'ForegroundColor'),'reset');
    
    %fprintf('test %f', blocks(block))
    %TODO: CHECK LIB IN A MORE PROPER WAY
    if(strcmp( get_param(blocks(block),'Parent'), 'eml_lib')==0)
    set_param(blocks(block),'ForegroundColor','black');
    end
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    %blockMap(blocks(block))=uDat;
    else
    %uDat=blockMap(blocks(block));
    uDat= get_param(blocks(block), 'UserData');
    uDat.origColor=get_param(blocks(block),'ForegroundColor');
    %uDat.opName='reset';
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    %blockMap(blocks(block))=uDat;
    %TODO: CHECK LIB IN A MORE PROPER WAY
    if(strcmp( get_param(blocks(block),'Parent'), 'eml_lib')==0)
    set_param(blocks(block),'ForegroundColor','black');
    end
    %set_param(blocks(block),'HiliteAncestors', mapObj(uDat.opName));   
    highlight_block(blocks(block))
    end
    end
    for line=1:length(lines),
    bDat=get_param(get_param(lines(line),'SrcBlockHandle'),'UserData');
    
    if(isKey(bDat.lineMap,getfullname(lines(line))))
    uDat=bDat.lineMap(getfullname(lines(line)));
    set_param(lines(line),'HiliteAncestors', mapObj(uDat.opName));
    else
        
    uDat=BlockDat('black','reset');
    bDat.lineMap(getfullname(lines(line)))=uDat;    
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat)
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    end
    end
   elseif (strcmp(mymodus,'normal')==1)
    for block=1:length(blocks),
    
    if(isempty(get_param(blocks(block), 'UserData')))
    set_param(blocks(block),'HiliteAncestors', 'none');
%    set_param(blocks(block),'ForegroundColor','black')
    uDat=BlockDat(get_param(blocks(block),'ForegroundColor'),'reset');
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    %blockMap(blocks(block))=uDat;
    else
        uDat=get_param(blocks(block), 'UserData');
    set_param(blocks(block),'HiliteAncestors', 'none');
    
    if(strcmp( get_param(blocks(block),'Parent'), 'eml_lib')==0)
    set_param(blocks(block),'ForegroundColor',uDat.origColor);
    end
    end
    end
    %%%%
    for line=1:length(lines),
    bDat=get_param(get_param(lines(line),'SrcBlockHandle'),'UserData');
    if(isKey(bDat.lineMap,getfullname(lines(line))))
    
    set_param(lines(line),'HiliteAncestors', 'none');
    else
    set_param(lines(line),'HiliteAncestors', 'none');    
    uDat=BlockDat('black','reset');
    bDat.lineMap(getfullname(lines(line)))=uDat;    
    
    end
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat)
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    end
   
   end
end

%% Define the schema function for first menu item.
function schema = addSimulinkBlock(callbackInfo)
global ckMode;
  schema = sl_container_schema;
  schema.label  = 'DeltaSimulink Operations';
  % Get marked Blocks
  if(strcmp(ckMode,'normal')==1)
   schema.state = 'Disabled';
   else
   schema.state = 'Enabled';    
   end
  % Get mouse position
  mousePosition = get(0,'PointerLocation');
  selectedBlocks=0;
  selectedConnections=0;
  
  availableOperations = cellstr({'add','remove','modify','replace','reset'});
  %childFunc=cell(1, length(availableOperations));
  for operation=1:length(availableOperations),  
     opName = availableOperations{operation};
     childFunc(operation) = {{@deltaSimulinkHandler,{opName,selectedBlocks,selectedConnections,mousePosition}}};
  end
  schema.childrenFcns = childFunc;
end 

function schema = deltaSimulinkHandler(callbackInfo)
   schema = sl_action_schema;
   schema.label = callbackInfo.userdata{1};	
   schema.userdata = callbackInfo.userdata;
   schema.callback = @slAddLibraryBlock; 
end

% SUB-FUNCTIONS
 function slAddLibraryBlock(inArgs)

%colorsForOp = cellstr({'add','remove','modify','replace','reset'});
mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
 opName     = inArgs.Userdata{1};
 selectedBlocks     = inArgs.Userdata{2};
 selectedLines     = inArgs.Userdata{3};
 mousePosition = inArgs.Userdata{4};
 blocks=find_system( 'FindAll','on',  'Selected', 'on', 'Type', 'Block');
 lines=find_system( 'FindAll','on',  'Selected', 'on', 'Type', 'Line');
%% Change Color
for block=1:length(blocks),
    
    if(not(isempty(get_param(blocks(block),'UserData'))))
    uDat=get_param(blocks(block),'UserData');
    uDat.opName=opName;
    %set_param(blocks(block),'HiliteAncestors', mapObj(opName));
    %set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block),'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    highlight_block(blocks(block));
    else
    uDat=BlockDat(get_param(blocks(block),'ForegroundColor'),opName);
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    %blockMap(blocks(block))=uDat;
    highlight_block(blocks(block));
    %set_param(blocks(block),'HiliteAncestors', mapObj(opName));
    
    end
    %uDat.opName=opName;
    %set_param(blocks(block), 'UserData', uDat);
    %set_param(blocks(block),'HiliteAncestors', mapObj(opName));
end

for line=1:length(lines),
    bDat=get_param(get_param(lines(line),'SrcBlockHandle'),'UserData');
    if(isKey(bDat.lineMap,getfullname(lines(line))))
    uDat=bDat.lineMap(getfullname(lines(line)));
    uDat.opName=opName;
    bDat.lineMap(getfullname(lines(line)))=uDat;
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat);
    %set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    highlight_line(lines(line))
    else
        
    uDat=BlockDat('black',opName);
    bDat.lineMap(getfullname(lines(line)))=uDat;    
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat);
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    %set_param(lines(line),'HiliteAncestors', mapObj(opName));
    highlight_line(lines(line))
    end
      
end
      
end
function highlight_block(block_id)
mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
 uDat=get_param(block_id,'UserData');
 opName=uDat.opName;
 hilite_system(block_id, mapObj(opName));
 parent=get_param(block_id, 'Parent');
 while not(strcmp(get_param(parent, 'Type') ,'block_diagram')) && not(isempty(get_param(parent,'UserData')))
    uDat=get_param(parent,'UserData');
    opName=uDat.opName;
    hilite_system(parent, mapObj(opName));
    parent=get_param(parent, 'Parent');
 end
end
 function highlight_line(line_id)
mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
bDat=get_param(get_param(line_id,'SrcBlockHandle'),'UserData');
    if(isKey(bDat.lineMap,getfullname(line_id)))
    uDat=bDat.lineMap(getfullname(line_id));
        set_param(line_id,'HiliteAncestors', mapObj(uDat.opName));
    parent=get_param(line_id, 'Parent');
 while not(strcmp(get_param(parent, 'Type') ,'block_diagram')) && not(isempty(get_param(parent,'UserData')))
    uDat=get_param(parent,'UserData');
    opName=uDat.opName;
    hilite_system(parent, mapObj(opName));
    parent=get_param(parent, 'Parent');
 end    
    else
    end
end
