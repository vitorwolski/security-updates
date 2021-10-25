package rest;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import estrutura.Parametros;

public class RepetidorRest
{
	public static void inicializaRepetidor()
	{
		JobDetail j = JobBuilder.newJob(RestSecurityUpdates.class).build();
		
		Trigger t = TriggerBuilder.newTrigger().withIdentity("RestMicrosoftSecurityUpdates")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever()).build();
		
		try
		{
			Scheduler s = StdSchedulerFactory.getDefaultScheduler();
			
			s.start();
			s.scheduleJob(j, t);
		}
		catch(SchedulerException e)
		{
			Parametros.getLog().escreveLog("RepetidorRest", "inicializarRepetidor()", "Erro ao tentar inicializar o repetidor rest.");
		}
	}
}
