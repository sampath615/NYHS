package gov.ny.state.doh.bhae.nyehms.web.spring.administration.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NyehmsUserActivityRepository extends JpaRepository<NyehmsUserActivity, NyehmsUserActivityId> {


    List<NyehmsUserActivity> findByWorkerIdOrderByActivityDateDesc(Long workerId);


}





/*
insert into
NYEHMS_USERS_ACTIVITY(WORKER_ID, ACTIVITY_DATE, ACTIVITY_BY, COMMENTS)
values(#{workerId}, sysdate, #{activityBy}, #{comments})

 save()

select
to_char(ACTIVITY_DATE, 'MM/DD/YYYY') "activityDate",
ACTIVITY_BY "activityBy",
COMMENTS "comments"
from NYEHMS_USERS_ACTIVITY
where worker_id = #{workerId}
order by ACTIVITY_DATE desc*/
