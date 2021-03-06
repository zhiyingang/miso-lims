/*
 * Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
 * MISO project contacts: Robert Davey @ TGAC
 * *********************************************************************
 *
 * This file is part of MISO.
 *
 * MISO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MISO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO.  If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

WarningTarget.completion = {
    tableWarnings: function(data, type, full){
      var warnings = [];
      var pool = full.pool;
      warnings = Warning.addWarnings([
        [pool.prioritySubprojectAliases && pool.prioritySubprojectAliases.length > 0, 'PRIORITY (' 
          + (pool.prioritySubprojectAliases.length == 1 ? pool.prioritySubprojectAliases[0] : 'MULTIPLE') + ')'],
        [pool.duplicateIndices, "(DUPLICATE INDICES)"],
        [pool.nearDuplicateIndices && !pool.duplicateIndices, "(NEAR-DUPLICATE INDICES)"],
        [pool.hasEmptySequence, "(MISSING INDEX)"],
        [pool.hasLowQualityLibraries, "(LOW QUALITY LIBRARIES)"],
        [pool.pooledElements && pool.pooledElements.some(function(dilution){
          return dilution.identityConsentLevel === 'Revoked';
        }), "(CONSENT REVOKED)"]
        ], warnings);
      return Warning.generateTableWarnings(data, warnings);
    }
};
