/*
 * Copyright 2015-2020 Jason Winning
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

package org.hypernomicon.view.populators;

import static org.hypernomicon.view.populators.Populator.CellValueType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.hypernomicon.model.records.HDT_Record;
import org.hypernomicon.model.records.HDT_RecordType;
import org.hypernomicon.view.wrappers.HyperTableCell;
import org.hypernomicon.view.wrappers.HyperTableRow;

public class CustomRecordPopulator extends Populator
{

//---------------------------------------------------------------------------

  @FunctionalInterface public static interface PopulateHandler { List<HDT_Record> handle(HyperTableRow row, boolean force); }

//---------------------------------------------------------------------------

  private HDT_RecordType recordType;
  private PopulateHandler handler;

//---------------------------------------------------------------------------

  public CustomRecordPopulator(HDT_RecordType recordType, PopulateHandler handler)
  {
    this.recordType = recordType;
    this.handler = handler;
  }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  @Override public CellValueType getValueType()                                 { return cvtRecord; }
  @Override public HDT_RecordType getRecordType(HyperTableRow row)              { return recordType; }
  @Override public HyperTableCell match(HyperTableRow row, HyperTableCell cell) { return equalMatch(row, cell); }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  @Override public List<HyperTableCell> populate(HyperTableRow row, boolean force)
  {
    return handler.handle(row, force).stream()
                                     .filter(record -> filter == null ? true : filter.test(record.getID()))
                                     .map(record -> new HyperTableCell(record, record.getCBText()))
                                     .collect(Collectors.toList());
  }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

}
