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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO. If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

package uk.ac.bbsrc.tgac.miso.webapp.controller.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import uk.ac.bbsrc.tgac.miso.core.data.Partition;
import uk.ac.bbsrc.tgac.miso.core.data.Project;
import uk.ac.bbsrc.tgac.miso.core.data.Run;
import uk.ac.bbsrc.tgac.miso.core.data.Sample;
import uk.ac.bbsrc.tgac.miso.core.data.SequencerPartitionContainer;
import uk.ac.bbsrc.tgac.miso.core.data.impl.view.PoolDilution;
import uk.ac.bbsrc.tgac.miso.core.data.type.HealthType;
import uk.ac.bbsrc.tgac.miso.core.util.LimsUtils;
import uk.ac.bbsrc.tgac.miso.service.ContainerService;
import uk.ac.bbsrc.tgac.miso.service.ProjectService;
import uk.ac.bbsrc.tgac.miso.service.RunService;
import uk.ac.bbsrc.tgac.miso.service.SampleService;

/**
 * uk.ac.bbsrc.tgac.miso.webapp.controller.rest
 * <p/>
 * Info
 * 
 * @author bianx
 */
@Controller
@RequestMapping("/rest/external")
@SessionAttributes("external")
public class ExternalRestController extends RestController {
  protected static final Logger log = LoggerFactory.getLogger(ExternalRestController.class);

  @Autowired
  private ProjectService projectService;
  @Autowired
  private ContainerService containerService;
  @Autowired
  private RunService runService;
  @Autowired
  private SampleService sampleService;

  public void setProjectService(ProjectService projectService) {
    this.projectService = projectService;
  }

  public void setContainerService(ContainerService containerService) {
    this.containerService = containerService;
  }

  public void setRunService(RunService runService) {
    this.runService = runService;
  }

  public void setSampleService(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  @GetMapping(value = "projects",  produces = "application/json")
  public @ResponseBody String jsonRest() throws IOException {
    StringBuilder sb = new StringBuilder();
    Collection<Project> lp = projectService.listAllProjects();
    int pi = 0;
    for (Project p : lp) {
      pi++;
      sb.append(jsonRestProjectList(p.getId()));
      if (pi < lp.size()) {
        sb.append(",");
      }
    }
    return "[" + sb.toString() + "]";
  }

  public String jsonRestProjectList(Long projectId) throws IOException {
    StringBuilder sb = new StringBuilder();

    Project p = projectService.get(projectId);
    sb.append("'id':'" + projectId + "'");
    sb.append(",");
    sb.append("'name':'" + p.getName() + "'");
    sb.append(",");
    sb.append("'alias':'" + p.getAlias() + "'");

    return "{" + sb.toString() + "}";
  }

  @GetMapping(value = "project/{projectId}",  produces = "application/json")
  public @ResponseBody String jsonRestProject(@PathVariable Long projectId, ModelMap model) throws IOException {
    StringBuilder sb = new StringBuilder();

    Project p = projectService.get(projectId);
    if (p == null) {
      throw new RestException("No project found with ID: " + projectId, Status.NOT_FOUND);
    }
    sb.append("'id':'" + projectId + "'");
    sb.append(",");
    sb.append("'name':'" + p.getName() + "'");
    sb.append(",");
    sb.append("'alias':'" + p.getAlias() + "'");
    sb.append(",");
    sb.append("'progress':'" + p.getProgress().name() + "'");
    sb.append(",");
    sb.append("'description':'" + p.getDescription() + "'");
    sb.append(",");

    sb.append("'samples':[");
    Collection<Sample> samples = sampleService.listByProjectId(projectId);
    if (samples.size() > 0) {
      int si = 0;
      for (Sample sample : samples) {
        si++;
        String sampleQubit = sample.getQCs().stream().filter(qc -> qc.getType().getName().contains("Qubit"))
            .sorted((a, b) -> b.getDate().compareTo(a.getDate())).findFirst().map(qc -> qc.getResults().toString()).orElse("not available");
        sb.append("{");
        sb.append("'alias':'" + sample.getAlias() + "'");
        sb.append(",");
        sb.append("'qcPassed':'" + (sample.getQcPassed() != null ? sample.getQcPassed().toString() : "") + "'");
        sb.append(",");

        sb.append("'receivedDate':'"
            + (sample.getReceivedDate() != null ? LimsUtils.formatDate(sample.getReceivedDate()) : "not available") + "'");
        sb.append(",");
        sb.append("'sampleType':'" + (sample.getSampleType() != null ? sample.getSampleType() : "") + "'");
        sb.append(",");
        sb.append("'sampleQubit':'" + sampleQubit + "'");
        sb.append("}");

        if (si < samples.size()) {
          sb.append(",");
        }

      }
    }
    sb.append("]");
    sb.append(",");

    sb.append("'runs':[");
    Collection<Run> runs = runService.listByProjectId(projectId);
    if (runs.size() > 0) {
      int ri = 0;
      for (Run run : runs) {
        ri++;
        if (run.getHealth() != HealthType.Failed) {
          ArrayList<String> runSamples = new ArrayList<>();
          Collection<SequencerPartitionContainer> spcs = containerService.listByRunId(run.getId());
          if (spcs.size() > 0) {
            for (SequencerPartitionContainer spc : spcs) {

              if (spc.getPartitions().size() > 0) {
                for (Partition spp : spc.getPartitions()) {
                  if (spp.getPool() != null && !spp.getPool().getPoolDilutions().isEmpty()) {
                    for (PoolDilution dilution : spp.getPool().getPoolDilutions()) {
                      if (dilution.getPoolableElementView().getProjectId().equals(p.getId())) {
                        runSamples.add(dilution.getPoolableElementView().getSampleAlias());
                      }
                    }
                  }
                }
              }
            }
          }

          sb.append("{");
          sb.append("'name':'" + run.getName() + "'");
          sb.append(",");
          sb.append("'status':'"
              + (run.getHealth() != null ? run.getHealth().getKey() : "") + "'");
          sb.append(",");
          sb.append("'startDate':'"
              + (run.getStartDate() != null ? run.getStartDate().toString() : "") + "'");
          sb.append(",");
          sb.append("'completionDate':'" + (run.getCompletionDate() != null
              ? run.getCompletionDate().toString()
              : "") + "'");
          sb.append(",");
          sb.append("'platformType':'" + (run.getSequencer().getInstrumentModel().getPlatformType() != null
              ? run.getSequencer().getInstrumentModel().getPlatformType().getKey()
              : "") + "'");
          sb.append(",");
          sb.append("'samples':[");
          if (runSamples.size() > 0) {
            int rsi = 0;
            for (String alias : runSamples) {
              rsi++;
              sb.append("{'sampleAlias':'" + alias + "'}");
              if (rsi < runSamples.size()) {
                sb.append(",");
              }
            }

          }
          sb.append("]");
          sb.append("}");
          if (ri < runs.size()) {
            sb.append(",");
          }
        }
      }
    }
    sb.append("]");

    return "{" + sb.toString() + "}";
  }

}
