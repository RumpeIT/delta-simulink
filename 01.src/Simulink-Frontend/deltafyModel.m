% Delta-Simulink
% Copyright (c) 2013, RIDT, All rights reserved.
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
function deltafyModel(blocks,lines)
mapObj = containers.Map({'add','remove','modify','replace','reset'}, {'green','red','blue','orangeWhite','black'});
    for block=1:length(blocks),
    %uDat=get_param(blocks(block), 'UserData');
    set_param(blocks(block), 'LinkStatus', 'inactive')
    if(isempty(get_param(blocks(block),'UserData')))
    uDat=BlockDat(get_param(blocks(block),'ForegroundColor'),'reset');
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    %blockMap(blocks(block))=uDat;
    else
     
    uDat= get_param(blocks(block), 'UserData');
    uDat.origColor=get_param(blocks(block),'ForegroundColor');
     
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    
    highlight_block(blocks(block))
    end
    uDat=get_param(blocks(block),'UserData');
    if(strcmp(uDat.origColor,'black')~=1)
        set_param(blocks(block),'ForegroundColor','black');
    end
    end
    for line=1:length(lines),
    bDat=get_param(get_param(lines(line),'SrcBlockHandle'),'UserData');
    if(isempty(bDat))
    uDat=BlockDat(get_param(get_param(lines(line),'SrcBlockHandle'),'ForegroundColor'),'reset');
    set_param(blocks(block), 'UserData',uDat);
    set_param(blocks(block), 'UserDataPersistent', 'on');
    bDat=uDat;
    end
    if(isKey(bDat.lineMap,getfullname(lines(line))))
    uDat=bDat.lineMap(getfullname(lines(line)));
    set_param(lines(line),'HiliteAncestors', mapObj(uDat.opName));
    else
        
    uDat=BlockDat('black','reset');
    bDat.lineMap(getfullname(lines(line)))=uDat;    
    set_param(get_param(lines(line),'SrcBlockHandle'),'UserData',bDat)
    set_param(get_param(lines(line),'SrcBlockHandle'), 'UserDataPersistent', 'on');
    
    end
	highlight_block(get_param(lines(line),'SrcBlockHandle'));
    end
    end
