package com.bottle.api.player.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bottle.api.player.vo.PlayerVO;
import com.bottle.api.ui.vo.CheckRecordVO;
import com.bottle.api.ui.vo.PlayerCheckRecordVO;

@Repository
public interface IPlayerDAO {
	public void insert(final PlayerVO vo);
	public PlayerVO selectOne_ByPhoneNumber(final long phoneNumber);
	public void updateAmountByPhoneNumber(final PlayerVO vo);
	public void updateScoreByPhoneNumber(final PlayerVO vo);
	
	public void updatePlayer(final PlayerVO vo);
	public List<PlayerVO> selectAll();
	public void insertPlayerCheckResult(final PlayerCheckRecordVO vo);
	public List<PlayerCheckRecordVO> selectPlayerCheckRecordVOList_ByPhoneNumber_OrderByResultID(@Param("phoneNumber") final long phoneNumber);
	public long selectMaxResultIdByPhoneNumber(@Param("phoneNumber") final long phoneNumber);
	public void insertRecordMap(final CheckRecordVO vo);
	public List<CheckRecordVO> selectRecordList_ByResultId(@Param("resultId") final long resultId);
}
