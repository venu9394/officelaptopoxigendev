package com.hhcl.init;

import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.hhcl.job.Job1;
import com.hhcl.job.Util;

public class MyApp {

	public synchronized void calljob(){
		try {
			JobDetail job1 = JobBuilder.newJob(Job1.class)
					.withIdentity("Job1", "group1").build();
			Properties prop=Util.loadPropertiesFile();
			Trigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("cronTrigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule(prop.getProperty("CornJoB")))
					.build();
			System.out.println(prop.getProperty("CornJoB") );
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);

			
			Thread.sleep(10000);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		
	}

}