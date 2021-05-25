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

package org.hypernomicon.model.records;

import static org.hypernomicon.model.HyperDB.db;
import static org.hypernomicon.util.Util.*;
import static org.hypernomicon.util.Util.MessageDialogType.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.hypernomicon.model.HDI_Schema;
import org.hypernomicon.model.Exceptions.HDB_InternalError;
import org.hypernomicon.model.Exceptions.HubChangedException;
import org.hypernomicon.model.Exceptions.RelationCycleException;
import org.hypernomicon.model.Exceptions.SearchKeyException;
import org.hypernomicon.model.HyperDB.Tag;
import org.hypernomicon.model.SearchKeys.SearchKeyword;

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

public interface HDT_Record
{
  int keyNdx();
  int getID();
  RecordType getType();
  void assignID() throws HDB_InternalError;

  void bringStoredCopyOnline(boolean dontRebuildMentions) throws RelationCycleException, SearchKeyException, HubChangedException;
  boolean hasStoredState();
  RecordState getRecordStateBackup();
  void restoreTo(RecordState backupState, boolean dontRebuildMentions) throws RelationCycleException, HDB_InternalError, SearchKeyException, HubChangedException;
  void saveToStoredState() throws HDB_InternalError;
  void writeStoredStateToXML(StringBuilder xml);

  void modifyNow();
  void viewNow();
  Instant getModifiedDate();
  Instant getViewDate();
  Instant getCreationDate();

  HDI_Schema getSchema(Tag tag);
  String getResultTextForTag(Tag tag);
  boolean getTagBoolean(Tag tag);
  Set<Tag> getAllTags();
  boolean isUnitable();
  boolean hasDesc();     // this means the record has a description, but not necessarily that it is connected directly to a MainText object (true for HDT_Term)
  boolean hasMainText(); // this means the record is directly connected to a MainText object (false for HDT_Term)
  void expire();
  boolean isExpired();
  boolean isDummy();
  boolean changeID(int newID);

  void getAllStrings(List<String> list, boolean searchLinkedRecords);
  String name();
  Tag getNameTag();
  void setName(String str);
  String listName();
  String getNameEngChar();
  String getCBText();
  String getXMLObjectName();
  String getSortKey();
  String makeSortKey();
  String getSortKeyAttr();
  String getSearchKey();
  String getFirstActiveKeyWord();
  void setSearchKey(String newKey) throws SearchKeyException;
  void setSearchKey(String newKey, boolean noMod, boolean dontRebuildMentions) throws SearchKeyException;

  void resolvePointers() throws HDB_InternalError;
  void updateSortKey();
  List<SearchKeyword> getSearchKeys();

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  static boolean isEmpty(HDT_Record record)
  {
    try { return isEmptyThrowsException(record); }
    catch (HDB_InternalError e) { messageDialog(e.getMessage(), mtError); }

    return true;
  }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  static boolean isEmptyThrowsException(HDT_Record record) throws HDB_InternalError
  {
    if ((record == null) || record.isExpired()) return true;

    if (record.getID() < 1)
      throw new HDB_InternalError(28883);

    return db.records(record.getType()).getByID(record.getID()) == null;
  }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

}
