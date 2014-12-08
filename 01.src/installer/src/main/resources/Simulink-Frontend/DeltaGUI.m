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
% License along with this project.function varargout = DeltaGUI(varargin)
% DELTAGUI MATLAB code for DeltaGUI.fig
%      DELTAGUI, by itself, creates a new DELTAGUI or raises the existing
%      singleton*.
%
%      H = DELTAGUI returns the handle to a new DELTAGUI or the handle to
%      the existing singleton*.
%
%      DELTAGUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in DELTAGUI.M with the given input arguments.
%
%      DELTAGUI('Property','Value',...) creates a new DELTAGUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before DeltaGUI_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to DeltaGUI_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help DeltaGUI

% Last Modified by GUIDE v2.5 07-Jan-2014 04:38:18

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @DeltaGUI_OpeningFcn, ...
                   'gui_OutputFcn',  @DeltaGUI_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
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
% varargin   command line arguments to DeltaGUI (see VARARGIN)

% Choose default command line output for DeltaGUI
handles.output = hObject;
handles.deltaEditField='C:\MATLAB\Delta';
handles.modelEditField='C:\MATLAB\Model';
handles.productEditField='C:\MATLAB\Product';
handles.configEditField='C:\MATLAB\DefaultConfig.delta';
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
model_dir=handles.modelEditField;
delta_dir=handles.deltaEditField;
conf_file=handles.configEditField;
product_dir=handles.productEditField;
DeltaSimulinkJARPath=getDeltaSimulinkJARPath();
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
folder_name = uigetdir('C:\','Select Core Model path');
%if(Filename)

set(handles.modelEditField, 'String', folder_name);
%end
% --- Executes on button press in DeltaFolderButton.
function DeltaFolderButton_Callback(hObject, eventdata, handles)
% hObject    handle to DeltaFolderButton (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
folder_name = uigetdir('C:\','Select Delta Model path');
set(handles.deltaEditField, 'String', folder_name);


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
folder_name = uigetdir('C:\','Select Product Output path');
set(handles.productEditField, 'String', folder_name);


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
[FileName,PathName] = uigetfile('*.delta','Select the delta config file');
Fullname=strcat(PathName,FileName);
set(handles.configEditField, 'String', Fullname);


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
