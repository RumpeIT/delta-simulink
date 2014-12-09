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
function [status,incompatibleBlocks] = chkBlocks(blocks, operation)
    dS_10_incompatible_blocks={};
    status=0;
    if(strcmp(getDSVersion(),'1.0'))
        allowed_blocks_DS_10={'Outport','Inport','SubSystem','ModelReference'};
        if (strcmp(operation,'add'))
        allowed_blocks_DS_10={'Outport','Inport','SubSystem','ModelReference'};
        end
        if (strcmp(operation,'remove'))
        allowed_blocks_DS_10={'Outport','Inport','SubSystem','ModelReference'};
        end
        if (strcmp(operation,'replace'))
        allowed_blocks_DS_10={'SubSystem','ModelReference'};
        end
        if (strcmp(operation,'modify'))
            allowed_blocks_DS_10={'SubSystem','ModelReference'};
        end
        for index=1:numel(blocks)
            block=blocks(index);
            blocktype =get_param(block,'BlockType');
            if ~ismember(blocktype,allowed_blocks_DS_10)
                blockname=getfullname(block);
                %append(dS_10_incompatible_blocks, blockname);
                dS_10_incompatible_blocks=[dS_10_incompatible_blocks;{blockname}];
            end
        end
    elseif (strcmp(Delta_Simulink_Version,'2.0'))
    %TODO: Delat Simulink 2.0 specific code
    else
    
    end
    if(~isempty(dS_10_incompatible_blocks))
        status=1;
        incompatibleBlocks=dS_10_incompatible_blocks;
    else
        status=0;
        incompatibleBlocks=dS_10_incompatible_blocks;
        
    end
end
            
         
    