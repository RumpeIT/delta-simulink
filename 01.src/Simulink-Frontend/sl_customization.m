% Delta-Simulink
% Copyright (c) 2013, RIT, All rights reserved.
% 
% This project is free software; you can redistribute it and/or
% modify it under the terms of the GNU Lesser General Public
% License as published by the Free Software Foundation; either
% version 3.0 of the License, or (at your option) any later version.
% This library is distributed in the hope that it will be useful,
% but WITHOUT ANY WARRANTY; without even the implied warranty of
% MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
% Lesser General Public License for more details.
% 
% You should have received a copy of the GNU Lesser General Public
% License along with this project.

function sl_customization(cm)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%TODO Add RWTH custom header
%load with
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

global ckMode;

%set_param('mymodel/Test', 'OpenFcn', 'testvar')
%if(lineMap==[])

    ckMode='normal';
%end
%m=mc.deltasimulink.simulink2montiarc.Simulink2MontiArcConverterHelper();
%m=javaObjectEDT('mc.deltasimulink.simulink2montiarc.Simulink2MontiArcConverterHelper');
% deltaTool=mc.deltasimulink.simulink2montiarc.DeltaSimulinkTool();
appendClassPath('C:\MATLAB_DL\lib\delta-simulink-be-1.3.0-SNAPSHOT.jar');
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
  schema.label  = 'DeltaSimulink Configuration';
  mymods= cellstr({'Apply Delta to Model File','Show me Corresponding Delta'});
  
    mymod=char(mymods{1});
  childFunc(1)={{@applyDelta,{mymod}}};
      mymod=char(mymods{2});
  childFunc(2)={{@showDelta,{mymod}}};
 
  schema.childrenFcns = childFunc;
end
function schema = applyDelta(callbackInfo)
   schema = sl_action_schema;
   schema.label = callbackInfo.userdata{1};
   mymod=callbackInfo.userdata{1};
   schema.userdata = callbackInfo.userdata;
   schema.callback = @applyDeltaCallBack;
end
function applyDeltaCallBack(inArgs)
DeltaGUI;

end
function schema = showDelta(callbackInfo)
   schema = sl_action_schema;
   schema.label = callbackInfo.userdata{1};
   mymod=callbackInfo.userdata{1};
   schema.userdata = callbackInfo.userdata;
   schema.callback = @showDeltaCallBack;
end
function showDeltaCallBack(inArgs)
m=mc.deltasimulink.simulink2montiarc.Simulink2MontiArcConverterHelper();
if(~isempty(gcs))
uuid = char(java.util.UUID.randomUUID);
outfile=strcat('tmp/',uuid,'.txt');
m.convert(pwd, gcs, outfile);
open(outfile);
end
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
   %parent_model=getParentModel(gcs);
   %blocks=find_system(parent_model,'FindAll','on', 'Type', 'Block');
   %lines=find_system(parent_model,'FindAll','on', 'Type', 'Line');
   parent_models=find_system('type', 'block_diagram', 'open','on');
   for index=1:numel(parent_models)
    parent_model=parent_models(index);
    blocks=find_system(parent_model,'FindAll','on', 'Type', 'Block');
    lines=find_system(parent_model,'FindAll','on', 'Type', 'Line');
    if(strcmp(mymodus,'delta')==1)
        deltafyModel(blocks,lines);
    elseif (strcmp(mymodus,'normal')==1)
    normalizeModel(blocks,lines);
   
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
  %if (strcmp(Delta_Simulink_Version,'1.0'))
  %end
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
 blocks=find_system(gcs, 'FindAll','on',  'Selected', 'on', 'Type', 'Block');
 lines=find_system( 'FindAll','on',  'Selected', 'on', 'Type', 'Line');
 sys_handle=get_param(gcs,'handle');
 blocks= blocks(find(blocks~=sys_handle));
 [status,list]=chkBlocks(blocks,opName);
%% Change Color
if status~=0
    warnmsg={'The following blocks are not compatible with the Delta operation you chose:'};
    warnmsg=[warnmsg,list];
    warnmsg=[warnmsg,{'Please remove them from the selection or choose another operation'}];
    warnmsg=[warnmsg,{'DeltaSimulink 2.0 will support arbitrary Deltas '}];
    warndlg(warnmsg);
else
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
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    set_param(lines(line),'HiliteAncestors', mapObj(opName));
    else
        
    uDat=BlockDat('black',opName);
    bDat.lineMap(getfullname(lines(line)))=uDat;    
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat);
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    set_param(lines(line),'HiliteAncestors', mapObj(opName));
    end
    highlight_block(get_param(lines(line),'SrcBlockHandle'));
end

    
end

      
end
