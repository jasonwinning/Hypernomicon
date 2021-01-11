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

package org.hypernomicon.util;

public interface Magnitude<T extends Comparable<? super T>> extends Comparable<T>
{

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

  public default boolean isLessThan            (T o) { return this.compareTo(o) <  0; }
  public default boolean isLessThanOrEqualTo   (T o) { return this.compareTo(o) <= 0; }
  public default boolean isGreaterThan         (T o) { return this.compareTo(o) >  0; }
  public default boolean isGreaterThanOrEqualTo(T o) { return this.compareTo(o) >= 0; }

//---------------------------------------------------------------------------
//---------------------------------------------------------------------------

}
