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
function normalizeModel(blocks,lines)   
    for block=1:length(blocks),
    set_param(blocks(block), 'LinkStatus', 'none')
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