/*
 * Copyright 2015-2021 Jason Winning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.hypernomicon.view.tabs;

import java.io.IOException;
import java.util.prefs.Preferences;

import org.hypernomicon.App;
import org.hypernomicon.model.records.HDT_Record;
import org.hypernomicon.model.records.HDT_RecordWithConnector;
import org.hypernomicon.view.mainText.MainTextWrapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

public abstract class HyperNodeTab<HDT_RT extends HDT_Record, HDT_CT extends HDT_RecordWithConnector> extends HyperTab<HDT_RT, HDT_CT>
{
  final NodeTabCtrlr<HDT_RT, HDT_CT> ctrlr;

  @Override public final MainTextWrapper mainTextWrapper() { return ctrlr.mainTextWrapper(); }
  @Override public String recordName()                     { return ctrlr.nameCtrl().getText(); }
  @Override void updateWebButtons(Preferences node)        { ctrlr.updateWebButtons(node); }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  HyperNodeTab(Tab tab) throws IOException
  {
    FXMLLoader loader = new FXMLLoader(App.class.getResource("view/tabs/NodeTab.fxml"));
    tab.setContent(loader.load());
    ctrlr = loader.getController();
  }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

}
