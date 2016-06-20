function varargout = DeltaGUI(varargin)
%DELTAGUI M-file for DeltaGUI.fig
%      DELTAGUI, by itself, creates a new DELTAGUI or raises the existing
%      singleton*.
%
%      H = DELTAGUI returns the handle to a new DELTAGUI or the handle to
%      the existing singleton*.
%
%      DELTAGUI('Property','Value',...) creates a new DELTAGUI using the
%      given property value pairs. Unrecognized properties are passed via
%      varargin to DeltaGUI_OpeningFcn.  This calling syntax produces a
%      warning when there is an existing singleton*.
%
%      DELTAGUI('CALLBACK') and DELTAGUI('CALLBACK',hObject,...) call the
%      local function named CALLBACK in DELTAGUI.M with the given input
%      arguments.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help DeltaGUI

% Last Modified by GUIDE v2.5 08-Dec-2014 16:59:02

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @DeltaGUI_OpeningFcn, ...
                   'gui_OutputFcn',  @DeltaGUI_OutputFcn, ...
                   'gui_LayoutFcn',  @DeltaGUI_LayoutFcn, ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
   gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before DeltaGUI is made visible.
function DeltaGUI_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   unrecognized PropertyName/PropertyValue pairs from the
%            command line (see VARARGIN)

% Choose default command line output for DeltaGUI
handles.output = hObject;

global model_dir;
global delta_dir;
global conf_file;
global product_dir;
handles.deltaEditField=delta_dir;
handles.modelEditField=model_dir;
handles.productEditField=product_dir;
handles.configEditField=conf_file;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes DeltaGUI wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = DeltaGUI_OutputFcn(hObject, eventdata, handles)
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on button press in applyButton.
function applyButton_Callback(hObject, eventdata, handles)
% hObject    handle to applyButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global model_dir;
global delta_dir;
global conf_file;
global product_dir;
global DeltaSimulinkJARPath;
model_dir=handles.modelEditField;
delta_dir=handles.deltaEditField;
conf_file=handles.configEditField;
product_dir=handles.productEditField;
javacommand=['java -jar' ' ' DeltaSimulinkJARPath ' ' model_dir ' ' delta_dir ' ' conf_file ' ' product_dir];
system(javacommand, '-echo');

% --- Executes on button press in abortButton.
function abortButton_Callback(hObject, eventdata, handles)
% hObject    handle to abortButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
close(gcbf);

% --- Executes on button press in ModelFolderButton.
function ModelFolderButton_Callback(hObject, eventdata, handles)
% hObject    handle to ModelFolderButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global model_dir;
model_dir = uigetdir(model_dir,'Select Core Model path');
%if(Filename)

set(handles.modelEditField, 'String', model_dir);

% --- Executes on button press in DeltaFolderButton.
function DeltaFolderButton_Callback(hObject, eventdata, handles)
% hObject    handle to DeltaFolderButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global delta_dir;
delta_dir = uigetdir(delta_dir,'Select Core Model path');
%if(Filename)

set(handles.deltaEditField, 'String', delta_dir);


function deltaEditField_Callback(hObject, eventdata, handles)
% hObject    handle to deltaEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of deltaEditField as text
%        str2double(get(hObject,'String')) returns contents of deltaEditField as a double


% --- Executes during object creation, after setting all properties.
function deltaEditField_CreateFcn(hObject, eventdata, handles)
% hObject    handle to deltaEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function modelEditField_Callback(hObject, eventdata, handles)
% hObject    handle to modelEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of modelEditField as text
%        str2double(get(hObject,'String')) returns contents of modelEditField as a double


% --- Executes during object creation, after setting all properties.
function modelEditField_CreateFcn(hObject, eventdata, handles)
% hObject    handle to modelEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in ProductFolderButton.
function ProductFolderButton_Callback(hObject, eventdata, handles)
% hObject    handle to ProductFolderButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global product_dir;
product_dir = uigetdir(delta_dir,'Select Core Model path');
%if(Filename)

set(handles.deltaEditField, 'String', product_dir);


function productEditField_Callback(hObject, eventdata, handles)
% hObject    handle to productEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of productEditField as text
%        str2double(get(hObject,'String')) returns contents of productEditField as a double


% --- Executes during object creation, after setting all properties.
function productEditField_CreateFcn(hObject, eventdata, handles)
% hObject    handle to productEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in ConfigFileButton.
function ConfigFileButton_Callback(hObject, eventdata, handles)
% hObject    handle to ConfigFileButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global conf_file;
[FileName,PathName] = uigetfile('*.delta','Select the delta config file');
conf_file=strcat(PathName,FileName);
set(handles.configEditField, 'String', conf_file);


function configEditField_Callback(hObject, eventdata, handles)
% hObject    handle to configEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of configEditField as text
%        str2double(get(hObject,'String')) returns contents of configEditField as a double


% --- Executes during object creation, after setting all properties.
function configEditField_CreateFcn(hObject, eventdata, handles)
% hObject    handle to configEditField (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Creates and returns a handle to the GUI figure. 
function h1 = DeltaGUI_LayoutFcn(policy)
% policy - create a new figure or use a singleton. 'new' or 'reuse'.

persistent hsingleton;
if strcmpi(policy, 'reuse') & ishandle(hsingleton)
    h1 = hsingleton;
    return;
end

appdata = [];
appdata.GUIDEOptions = struct(...
    'active_h', [], ...
    'taginfo', struct(...
    'figure', 2, ...
    'edit', 5, ...
    'pushbutton', 7, ...
    'uipanel', 2, ...
    'text', 5), ...
    'override', 0, ...
    'release', 13, ...
    'resize', 'none', ...
    'accessibility', 'callback', ...
    'mfile', 1, ...
    'callbacks', 1, ...
    'singleton', 1, ...
    'syscolorfig', 1, ...
    'blocking', 0, ...
    'lastSavedFile', 'C:\MATLAB_DL\DeltaGUI.m', ...
    'lastFilename', 'C:\MATLAB_DL\DeltaGUI.fig');
appdata.lastValidTag = 'figure1';
appdata.GUIDELayoutEditor = [];
appdata.initTags = struct(...
    'handle', [], ...
    'tag', 'figure1');

h1 = figure(...
'Units','characters',...
'PaperUnits',get(0,'defaultfigurePaperUnits'),...
'Color',[0.941176470588235 0.941176470588235 0.941176470588235],...
'Colormap',[0 0 0.5625;0 0 0.625;0 0 0.6875;0 0 0.75;0 0 0.8125;0 0 0.875;0 0 0.9375;0 0 1;0 0.0625 1;0 0.125 1;0 0.1875 1;0 0.25 1;0 0.3125 1;0 0.375 1;0 0.4375 1;0 0.5 1;0 0.5625 1;0 0.625 1;0 0.6875 1;0 0.75 1;0 0.8125 1;0 0.875 1;0 0.9375 1;0 1 1;0.0625 1 1;0.125 1 0.9375;0.1875 1 0.875;0.25 1 0.8125;0.3125 1 0.75;0.375 1 0.6875;0.4375 1 0.625;0.5 1 0.5625;0.5625 1 0.5;0.625 1 0.4375;0.6875 1 0.375;0.75 1 0.3125;0.8125 1 0.25;0.875 1 0.1875;0.9375 1 0.125;1 1 0.0625;1 1 0;1 0.9375 0;1 0.875 0;1 0.8125 0;1 0.75 0;1 0.6875 0;1 0.625 0;1 0.5625 0;1 0.5 0;1 0.4375 0;1 0.375 0;1 0.3125 0;1 0.25 0;1 0.1875 0;1 0.125 0;1 0.0625 0;1 0 0;0.9375 0 0;0.875 0 0;0.8125 0 0;0.75 0 0;0.6875 0 0;0.625 0 0;0.5625 0 0],...
'IntegerHandle','off',...
'InvertHardcopy',get(0,'defaultfigureInvertHardcopy'),...
'MenuBar','none',...
'Name','DeltaGUI',...
'NumberTitle','off',...
'PaperPosition',get(0,'defaultfigurePaperPosition'),...
'PaperSize',get(0,'defaultfigurePaperSize'),...
'PaperType',get(0,'defaultfigurePaperType'),...
'Position',[103.8 44.0769230769231 99 17.6153846153846],...
'Resize','off',...
'HandleVisibility','callback',...
'UserData',[],...
'Tag','figure1',...
'Visible','on',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'uipanel1';

h2 = uipanel(...
'Parent',h1,...
'Units','characters',...
'Title','Delta Config',...
'Tag','uipanel1',...
'Clipping','on',...
'Position',[2 4.46153846153846 89.8 12.3846153846154],...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'ModelFolderButton';

h3 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('ModelFolderButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[70 9.15384615384615 13.8 1.69230769230769],...
'String','Folder',...
'Tag','ModelFolderButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'DeltaFolderButton';

h4 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('DeltaFolderButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[70 6.76923076923077 14 1.69230769230769],...
'String','Folder',...
'Tag','DeltaFolderButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'deltaEditField';

h5 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'BackgroundColor',[1 1 1],...
'Callback',@(hObject,eventdata)DeltaGUI('deltaEditField_Callback',hObject,eventdata,guidata(hObject)),...
'Enable','off',...
'HorizontalAlignment','left',...
'Position',[15 6.69230769230769 52.6 1.61538461538462],...
'String','C:\MATLAB\Delta',...
'Style','edit',...
'CreateFcn', {@local_CreateFcn, @(hObject,eventdata)DeltaGUI('deltaEditField_CreateFcn',hObject,eventdata,guidata(hObject)), appdata} ,...
'Tag','deltaEditField');

appdata = [];
appdata.lastValidTag = 'modelEditField';

h6 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'BackgroundColor',[1 1 1],...
'Callback',@(hObject,eventdata)DeltaGUI('modelEditField_Callback',hObject,eventdata,guidata(hObject)),...
'Enable','off',...
'HorizontalAlignment','left',...
'Position',[14.8 9.15384615384615 52.8 1.69230769230769],...
'String','C:\MATLAB\Model',...
'Style','edit',...
'CreateFcn', {@local_CreateFcn, @(hObject,eventdata)DeltaGUI('modelEditField_CreateFcn',hObject,eventdata,guidata(hObject)), appdata} ,...
'Tag','modelEditField');

appdata = [];
appdata.lastValidTag = 'text1';

h7 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Position',[1.2 9.15384615384615 11.8 1.61538461538462],...
'String','Model',...
'Style','text',...
'Tag','text1',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'text2';

h8 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Position',[1.4 6.53846153846154 11.6 1.53846153846154],...
'String','Delta',...
'Style','text',...
'Tag','text2',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'ProductFolderButton';

h9 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('ProductFolderButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[69.8 4.38461538461539 14 1.69230769230769],...
'String','Folder',...
'Tag','ProductFolderButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'productEditField';

h10 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'BackgroundColor',[1 1 1],...
'Callback',@(hObject,eventdata)DeltaGUI('productEditField_Callback',hObject,eventdata,guidata(hObject)),...
'Enable','off',...
'HorizontalAlignment','left',...
'Position',[14.8 4.30769230769231 52.6 1.61538461538462],...
'String','C:\MATLAB\Product',...
'Style','edit',...
'CreateFcn', {@local_CreateFcn, @(hObject,eventdata)DeltaGUI('productEditField_CreateFcn',hObject,eventdata,guidata(hObject)), appdata} ,...
'Tag','productEditField');

appdata = [];
appdata.lastValidTag = 'text3';

h11 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Position',[1.2 4.15384615384615 11.6 1.53846153846154],...
'String','Product',...
'Style','text',...
'Tag','text3',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'ConfigFileButton';

h12 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('ConfigFileButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[69.6 1.69230769230769 14 1.69230769230769],...
'String','File',...
'Tag','ConfigFileButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'configEditField';

h13 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'BackgroundColor',[1 1 1],...
'Callback',@(hObject,eventdata)DeltaGUI('configEditField_Callback',hObject,eventdata,guidata(hObject)),...
'Enable','off',...
'HorizontalAlignment','left',...
'Position',[14.6 1.61538461538462 52.6 1.61538461538462],...
'String','C:\MATLAB\DeltaConfig.delta',...
'Style','edit',...
'CreateFcn', {@local_CreateFcn, @(hObject,eventdata)DeltaGUI('configEditField_CreateFcn',hObject,eventdata,guidata(hObject)), appdata} ,...
'Tag','configEditField');

appdata = [];
appdata.lastValidTag = 'text4';

h14 = uicontrol(...
'Parent',h2,...
'Units','characters',...
'Position',[1 1.46153846153846 11.6 1.53846153846154],...
'String','ConfigFile',...
'Style','text',...
'Tag','text4',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'applyButton';

h15 = uicontrol(...
'Parent',h1,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('applyButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[54.6 2.15384615384615 15.2 1.84615384615385],...
'String','Apply',...
'Tag','applyButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );

appdata = [];
appdata.lastValidTag = 'abortButton';

h16 = uicontrol(...
'Parent',h1,...
'Units','characters',...
'Callback',@(hObject,eventdata)DeltaGUI('abortButton_Callback',hObject,eventdata,guidata(hObject)),...
'Position',[75.2 2.15384615384615 14.6 1.76923076923077],...
'String','Abort',...
'Tag','abortButton',...
'CreateFcn', {@local_CreateFcn, blanks(0), appdata} );


hsingleton = h1;


% --- Set application data first then calling the CreateFcn. 
function local_CreateFcn(hObject, eventdata, createfcn, appdata)

if ~isempty(appdata)
   names = fieldnames(appdata);
   for i=1:length(names)
       name = char(names(i));
       setappdata(hObject, name, getfield(appdata,name));
   end
end

if ~isempty(createfcn)
   if isa(createfcn,'function_handle')
       createfcn(hObject, eventdata);
   else
       eval(createfcn);
   end
end


% --- Handles default GUIDE GUI creation and callback dispatch
function varargout = gui_mainfcn(gui_State, varargin)

gui_StateFields =  {'gui_Name'
    'gui_Singleton'
    'gui_OpeningFcn'
    'gui_OutputFcn'
    'gui_LayoutFcn'
    'gui_Callback'};
gui_Mfile = '';
for i=1:length(gui_StateFields)
    if ~isfield(gui_State, gui_StateFields{i})
        error(message('MATLAB:guide:StateFieldNotFound', gui_StateFields{ i }, gui_Mfile));
    elseif isequal(gui_StateFields{i}, 'gui_Name')
        gui_Mfile = [gui_State.(gui_StateFields{i}), '.m'];
    end
end

numargin = length(varargin);

if numargin == 0
    % DELTAGUI
    % create the GUI only if we are not in the process of loading it
    % already
    gui_Create = true;
elseif local_isInvokeActiveXCallback(gui_State, varargin{:})
    % DELTAGUI(ACTIVEX,...)
    vin{1} = gui_State.gui_Name;
    vin{2} = [get(varargin{1}.Peer, 'Tag'), '_', varargin{end}];
    vin{3} = varargin{1};
    vin{4} = varargin{end-1};
    vin{5} = guidata(varargin{1}.Peer);
    feval(vin{:});
    return;
elseif local_isInvokeHGCallback(gui_State, varargin{:})
    % DELTAGUI('CALLBACK',hObject,eventData,handles,...)
    gui_Create = false;
else
    % DELTAGUI(...)
    % create the GUI and hand varargin to the openingfcn
    gui_Create = true;
end

if ~gui_Create
    % In design time, we need to mark all components possibly created in
    % the coming callback evaluation as non-serializable. This way, they
    % will not be brought into GUIDE and not be saved in the figure file
    % when running/saving the GUI from GUIDE.
    designEval = false;
    if (numargin>1 && ishghandle(varargin{2}))
        fig = varargin{2};
        while ~isempty(fig) && ~ishghandle(fig,'figure')
            fig = get(fig,'parent');
        end
        
        designEval = isappdata(0,'CreatingGUIDEFigure') || isprop(fig,'__GUIDEFigure');
    end
        
    if designEval
        beforeChildren = findall(fig);
    end
    
    % evaluate the callback now
    varargin{1} = gui_State.gui_Callback;
    if nargout
        [varargout{1:nargout}] = feval(varargin{:});
    else       
        feval(varargin{:});
    end
    
    % Set serializable of objects created in the above callback to off in
    % design time. Need to check whether figure handle is still valid in
    % case the figure is deleted during the callback dispatching.
    if designEval && ishghandle(fig)
        set(setdiff(findall(fig),beforeChildren), 'Serializable','off');
    end
else
    if gui_State.gui_Singleton
        gui_SingletonOpt = 'reuse';
    else
        gui_SingletonOpt = 'new';
    end

    % Check user passing 'visible' P/V pair first so that its value can be
    % used by oepnfig to prevent flickering
    gui_Visible = 'auto';
    gui_VisibleInput = '';
    for index=1:2:length(varargin)
        if length(varargin) == index || ~ischar(varargin{index})
            break;
        end

        % Recognize 'visible' P/V pair
        len1 = min(length('visible'),length(varargin{index}));
        len2 = min(length('off'),length(varargin{index+1}));
        if ischar(varargin{index+1}) && strncmpi(varargin{index},'visible',len1) && len2 > 1
            if strncmpi(varargin{index+1},'off',len2)
                gui_Visible = 'invisible';
                gui_VisibleInput = 'off';
            elseif strncmpi(varargin{index+1},'on',len2)
                gui_Visible = 'visible';
                gui_VisibleInput = 'on';
            end
        end
    end
    
    % Open fig file with stored settings.  Note: This executes all component
    % specific CreateFunctions with an empty HANDLES structure.

    
    % Do feval on layout code in m-file if it exists
    gui_Exported = ~isempty(gui_State.gui_LayoutFcn);
    % this application data is used to indicate the running mode of a GUIDE
    % GUI to distinguish it from the design mode of the GUI in GUIDE. it is
    % only used by actxproxy at this time.   
    setappdata(0,genvarname(['OpenGuiWhenRunning_', gui_State.gui_Name]),1);
    if gui_Exported
        gui_hFigure = feval(gui_State.gui_LayoutFcn, gui_SingletonOpt);

        % make figure invisible here so that the visibility of figure is
        % consistent in OpeningFcn in the exported GUI case
        if isempty(gui_VisibleInput)
            gui_VisibleInput = get(gui_hFigure,'Visible');
        end
        set(gui_hFigure,'Visible','off')

        % openfig (called by local_openfig below) does this for guis without
        % the LayoutFcn. Be sure to do it here so guis show up on screen.
        movegui(gui_hFigure,'onscreen');
    else
        gui_hFigure = local_openfig(gui_State.gui_Name, gui_SingletonOpt, gui_Visible);
        % If the figure has InGUIInitialization it was not completely created
        % on the last pass.  Delete this handle and try again.
        if isappdata(gui_hFigure, 'InGUIInitialization')
            delete(gui_hFigure);
            gui_hFigure = local_openfig(gui_State.gui_Name, gui_SingletonOpt, gui_Visible);
        end
    end
    if isappdata(0, genvarname(['OpenGuiWhenRunning_', gui_State.gui_Name]))
        rmappdata(0,genvarname(['OpenGuiWhenRunning_', gui_State.gui_Name]));
    end

    % Set flag to indicate starting GUI initialization
    setappdata(gui_hFigure,'InGUIInitialization',1);

    % Fetch GUIDE Application options
    gui_Options = getappdata(gui_hFigure,'GUIDEOptions');
    % Singleton setting in the GUI M-file takes priority if different
    gui_Options.singleton = gui_State.gui_Singleton;

    if ~isappdata(gui_hFigure,'GUIOnScreen')
        % Adjust background color
        if gui_Options.syscolorfig
            set(gui_hFigure,'Color', get(0,'DefaultUicontrolBackgroundColor'));
        end

        % Generate HANDLES structure and store with GUIDATA. If there is
        % user set GUI data already, keep that also.
        data = guidata(gui_hFigure);
        handles = guihandles(gui_hFigure);
        if ~isempty(handles)
            if isempty(data)
                data = handles;
            else
                names = fieldnames(handles);
                for k=1:length(names)
                    data.(char(names(k)))=handles.(char(names(k)));
                end
            end
        end
        guidata(gui_hFigure, data);
    end

    % Apply input P/V pairs other than 'visible'
    for index=1:2:length(varargin)
        if length(varargin) == index || ~ischar(varargin{index})
            break;
        end

        len1 = min(length('visible'),length(varargin{index}));
        if ~strncmpi(varargin{index},'visible',len1)
            try set(gui_hFigure, varargin{index}, varargin{index+1}), catch break, end
        end
    end

    % If handle visibility is set to 'callback', turn it on until finished
    % with OpeningFcn
    gui_HandleVisibility = get(gui_hFigure,'HandleVisibility');
    if strcmp(gui_HandleVisibility, 'callback')
        set(gui_hFigure,'HandleVisibility', 'on');
    end

    feval(gui_State.gui_OpeningFcn, gui_hFigure, [], guidata(gui_hFigure), varargin{:});

    if isscalar(gui_hFigure) && ishghandle(gui_hFigure)
        % Handle the default callbacks of predefined toolbar tools in this
        % GUI, if any
        guidemfile('restoreToolbarToolPredefinedCallback',gui_hFigure); 
        
        % Update handle visibility
        set(gui_hFigure,'HandleVisibility', gui_HandleVisibility);

        % Call openfig again to pick up the saved visibility or apply the
        % one passed in from the P/V pairs
        if ~gui_Exported
            gui_hFigure = local_openfig(gui_State.gui_Name, 'reuse',gui_Visible);
        elseif ~isempty(gui_VisibleInput)
            set(gui_hFigure,'Visible',gui_VisibleInput);
        end
        if strcmpi(get(gui_hFigure, 'Visible'), 'on')
            figure(gui_hFigure);
            
            if gui_Options.singleton
                setappdata(gui_hFigure,'GUIOnScreen', 1);
            end
        end

        % Done with GUI initialization
        if isappdata(gui_hFigure,'InGUIInitialization')
            rmappdata(gui_hFigure,'InGUIInitialization');
        end

        % If handle visibility is set to 'callback', turn it on until
        % finished with OutputFcn
        gui_HandleVisibility = get(gui_hFigure,'HandleVisibility');
        if strcmp(gui_HandleVisibility, 'callback')
            set(gui_hFigure,'HandleVisibility', 'on');
        end
        gui_Handles = guidata(gui_hFigure);
    else
        gui_Handles = [];
    end

    if nargout
        [varargout{1:nargout}] = feval(gui_State.gui_OutputFcn, gui_hFigure, [], gui_Handles);
    else
        feval(gui_State.gui_OutputFcn, gui_hFigure, [], gui_Handles);
    end

    if isscalar(gui_hFigure) && ishghandle(gui_hFigure)
        set(gui_hFigure,'HandleVisibility', gui_HandleVisibility);
    end
end

function gui_hFigure = local_openfig(name, singleton, visible)

% openfig with three arguments was new from R13. Try to call that first, if
% failed, try the old openfig.
if nargin('openfig') == 2
    % OPENFIG did not accept 3rd input argument until R13,
    % toggle default figure visible to prevent the figure
    % from showing up too soon.
    gui_OldDefaultVisible = get(0,'defaultFigureVisible');
    set(0,'defaultFigureVisible','off');
    gui_hFigure = openfig(name, singleton);
    set(0,'defaultFigureVisible',gui_OldDefaultVisible);
else
    gui_hFigure = openfig(name, singleton, visible);  
    %workaround for CreateFcn not called to create ActiveX
    if feature('HGUsingMATLABClasses')
        peers=findobj(findall(allchild(gui_hFigure)),'type','uicontrol','style','text');    
        for i=1:length(peers)
            if isappdata(peers(i),'Control')
                actxproxy(peers(i));
            end            
        end
    end
end

function result = local_isInvokeActiveXCallback(gui_State, varargin)

try
    result = ispc && iscom(varargin{1}) ...
             && isequal(varargin{1},gcbo);
catch
    result = false;
end

function result = local_isInvokeHGCallback(gui_State, varargin)

try
    fhandle = functions(gui_State.gui_Callback);
    result = ~isempty(findstr(gui_State.gui_Name,fhandle.file)) || ...
             (ischar(varargin{1}) ...
             && isequal(ishghandle(varargin{2}), 1) ...
             && (~isempty(strfind(varargin{1},[get(varargin{2}, 'Tag'), '_'])) || ...
                ~isempty(strfind(varargin{1}, '_CreateFcn'))) );
catch
    result = false;
end


