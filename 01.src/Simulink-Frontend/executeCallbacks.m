function executeCallbacks(obj,type)
%executeCallbacks - For internal use only.
%
% Executes all callbacks of the specified type for the block diagram.

%  Copyright 2011 The MathWorks, Inc.
%deltachk();
disp('Hello First');
callbacks = get_param(obj.Handle,'Callbacks');
assert(isempty(callbacks) || isstruct(callbacks));
if isfield(callbacks,type)
    f = callbacks.(type);
    % To keep things deterministic, we execute the callbacks in
    % alphabetical order by ID.
    ids = sort(fieldnames(f));
    for i=1:numel(ids)
        i_execute(obj,type,ids{i},f.(ids{i}));
    end
end
end

%------------------------------------------------
function i_execute(obj,type,id,fcn)
try
    fcn();
catch E
    DAStudio.warning('Simulink:utility:BlockDiagramExecutionError',...
            type,id,obj.Name,E.message);
end
end
